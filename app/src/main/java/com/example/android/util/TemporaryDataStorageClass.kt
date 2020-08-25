package com.example.android.util

import androidx.lifecycle.MutableLiveData
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.database.trainingweekEntityDao.TrainingWeek
import com.example.android.repository.Repository
import kotlinx.coroutines.*

class TemporaryDataStorageClass private constructor() {
    private object Holder {
        val INSTANCE = TemporaryDataStorageClass()
    }

    companion object {
        val instance = Holder.INSTANCE
    }


    private val ioScope = CoroutineScope(Dispatchers.IO)
    private var weeksList = mutableListOf<TrainingWeek>()
    private var trainingDayList = mutableListOf<TrainingDay>()
    private var exercisesLiveDataList = MutableLiveData<List<Exercise>>()

    var exercisesList = mutableListOf<Exercise>()
    var templateEntity = TrainingTemplate()
    var currentTrainingDay = TrainingDay()
    var weeksDaysExercisesMap = mutableMapOf<TrainingWeek, Map<TrainingDay, List<Exercise>>>()

    var weeksIdForDeleting = mutableListOf<Long>()
    var daysIdListForDeleting = mutableListOf<Long>()
    var exerciseIdListForDeleting = mutableListOf<Long>()


    fun startDataDownloading(templateId: Long, repository: Repository) {
        ioScope.launch {
            templateEntity = repository.getTemplate(templateId)
            weeksList = repository.getWeeksForCurrentTemplate(templateId)
            trainingDayList = repository.getTrainingDaysForAllWeek(getIdList(0))
            exercisesList = repository.getExercisesForAllDays(getIdList(1))
            setExercisesLocalId()
            withContext(Dispatchers.Main) {
                Util.endLoading.value = true
                Util.endLoading.value = false
            }
        }
    }

    private fun getIdList(switcher: Int): MutableList<Long> {
        val idList = mutableListOf<Long>()
        when (switcher) {
            0 -> weeksList.forEach { idList.add(it.weekId) }
            1 -> trainingDayList.forEach { idList.add(it.dayId) }
        }
        return idList
    }

    private fun setExercisesLocalId() {
        var newLocalId = 0
        exercisesList.forEach {
            newLocalId += 1
            it.localId = newLocalId
        }
    }


    //укладывает данные в коллекцию
    fun packDataAtMap() {
        weeksList.forEach {
            weeksDaysExercisesMap.put(
                returnSpecificWeek(it.weekNumber),
                returnMapDayExerciseList(it.weekNumber)
            )
        }
    }

    fun returnExerciseForRedaction(localId: Int): Exercise {
        return exercisesList.single { it.localId == localId }
    }

    //возвращает неделю из списка в зависимости от номера недели
    private fun returnSpecificWeek(weekNumber: Int): TrainingWeek {
        return weeksList.single { it.weekNumber == weekNumber }
    }

    //возвращает коллекцию из пар тренировочный день - список упражнений в зависимости от номера недели
    private fun returnMapDayExerciseList(weekNumber: Int): Map<TrainingDay, List<Exercise>> {
        val daysExercisesMap = mutableMapOf<TrainingDay, List<Exercise>>()
        trainingDayList.forEach { trainingDay ->
            if (trainingDay.weekNumber == weekNumber) {
                daysExercisesMap.put(
                    trainingDay,
                    returnExerciseListForSpecificDay(weekNumber, trainingDay.dayNumber)
                )
            }
        }
        return daysExercisesMap
    }

    //возращает список упражнений для конкретного тренировчного дня
    private fun returnExerciseListForSpecificDay(weekNumber: Int, dayNumber: Int): List<Exercise> {
        return exercisesList.filter { it.weekNumber == weekNumber && it.dayNumber == dayNumber }
    }


    //получает из списка Тренировочный день в зависимости от номера недели и дня
    private fun setCurrentTrainingDay(weekNumber: Int, dayNumber: Int) {
        currentTrainingDay =
            trainingDayList.single { it.weekNumber == weekNumber && it.dayNumber == dayNumber }
    }

    fun createTrainingTemplate() {
        val newTemplate = TrainingTemplate()
        saveNewTrainingTemplate(newTemplate)
    }

    fun createTrainingWeek(weekNumber: Int) {
        val newWeek = TrainingWeek()
        newWeek.weekNumber = weekNumber
        weeksList.add(newWeek)
        templateEntity.numberOfTrainingWeeks = weeksList.size
    }

    fun createTrainingDay(weekNumber: Int, dayNumber: Int) {
        //проверка существует ли уже такой день в коллекции
        val checkDay =
            trainingDayList.find { it.weekNumber == weekNumber && it.dayNumber == dayNumber }
        if (checkDay == null) {
            val newDay = TrainingDay()
            newDay.dayOfTheWeek = Util.returnDayOfTheWeek(dayNumber)
            newDay.weekNumber = weekNumber
            newDay.dayNumber = dayNumber
            saveTrainingDay(newDay)
        }
        setCurrentTrainingDay(weekNumber, dayNumber)
    }

    fun createNewExercise(name: String, sets: String, reps: String, weight: String) {
        val newExercise = Exercise()
        newExercise.exerciseName = name
        newExercise.set = sets
        newExercise.rep = reps
        newExercise.weight = weight
        newExercise.weekNumber = currentTrainingDay.weekNumber
        newExercise.dayNumber = currentTrainingDay.dayNumber
        if (exercisesList.isEmpty()) { newExercise.localId = 1 }
        else newExercise.localId = exercisesList.last().localId + 1
        addExerciseAtList(newExercise)
    }

    fun addExerciseAtList(exercise: Exercise) {
        exercisesList.add(exercise)
    }

    private fun saveTrainingDay(day: TrainingDay) {
        trainingDayList.add(day)
    }


    private fun saveNewTrainingTemplate(template: TrainingTemplate) {
        templateEntity = template
    }

    fun setNewTrainingTemplateName(name: String) {
        templateEntity.templateName = name
    }

    fun setNewTrainingTemplateDescription(description: String) {
        templateEntity.templateDescription = description
    }

    fun returnTemplateEntity(): TrainingTemplate {
        return templateEntity
    }

    fun deleteWeek(weekNumber: Int) {
        weeksList.forEach {
            if (it.weekNumber == weekNumber) {
                weeksIdForDeleting.add(it.weekId)
            }
        }
        weeksList.removeAll { it.weekNumber == weekNumber }
        templateEntity.numberOfTrainingWeeks = weeksList.size
        deleteDaysForSpecificWeek(weekNumber)
        deleteExercisesForSpecificWeek(weekNumber)
    }

    private fun deleteDaysForSpecificWeek(weekNumber: Int) {
        trainingDayList.forEach {
            if (it.weekNumber == weekNumber && it.dayId > 0) {
                daysIdListForDeleting.add(it.dayId)
            }
        }
        trainingDayList.removeAll { it.weekNumber == weekNumber }
    }

    private fun deleteExercisesForSpecificWeek(weekNumber: Int) {
        exercisesList.forEach {
            if (it.weekNumber == weekNumber && it.exerciseId > 0) {
                exerciseIdListForDeleting.add(it.exerciseId)
            }
        }
        exercisesList.removeAll { it.weekNumber == weekNumber }
    }

    fun deleteCurrentDay(repository: Repository) {
        trainingDayList.remove(currentTrainingDay)
        repository.deleteDay(currentTrainingDay.dayId)
    }

    fun deleteDayExercises(repository: Repository) {
        exercisesList.forEach {
            if (it.parentTrainingDayId == currentTrainingDay.dayId && it.exerciseId > 0) {
                exerciseIdListForDeleting.add(it.exerciseId)
            }
        }
        exercisesList.removeAll { it.parentTrainingDayId == currentTrainingDay.dayId }
        repository.deleteExercises(exerciseIdListForDeleting)
    }

    fun checkWeekExist(weekNumber: Int): Boolean {
        return weeksList.any { it.weekNumber == weekNumber }
    }

    fun checkExistDays(weekNumber: Int, dayNumber: Int): Boolean {
        return trainingDayList.any { it.weekNumber == weekNumber && it.dayNumber == dayNumber }
    }


    //очищает все данные в TemporaryDataStorage
    fun clearAllData() {
        weeksDaysExercisesMap.clear()
        exercisesList.clear()
        trainingDayList.clear()
        weeksList.clear()
    }

    fun deleteDataFromBase(repository: Repository) {
        repository.deleteWeeks(weeksIdForDeleting)
        repository.deleteDays(daysIdListForDeleting)
        repository.deleteExercises(exerciseIdListForDeleting)
    }

    fun returnExerciseLiveDataList(): MutableLiveData<List<Exercise>> {
        exercisesLiveDataList.value =
            exercisesList.filter { it.weekNumber == currentTrainingDay.weekNumber && it.dayNumber == currentTrainingDay.dayNumber }
        return exercisesLiveDataList
    }
}