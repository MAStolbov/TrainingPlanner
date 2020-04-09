package com.example.android.util

import androidx.lifecycle.MutableLiveData
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.database.trainingweekEntityDao.TrainingWeek
import com.example.android.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TemporaryDataStorageClass private constructor() {
    private object Holder {
        val INSTANCE = TemporaryDataStorageClass()
    }

    companion object {
        val instance = Holder.INSTANCE
    }


    private val ioScope = CoroutineScope(Dispatchers.IO)
    private var templateEntity = TrainingTemplate()
    private var weeksList = mutableListOf<TrainingWeek>()
    private var trainingDayList = mutableListOf<TrainingDay>()
    private var exercisesList = mutableListOf<Exercise>()
    private var exercisesLiveDataList = MutableLiveData<List<Exercise>>()
    private var trainingWeek = TrainingWeek()


    var week1:TrainingWeek? = null
    var week2:TrainingWeek? = null
    var week3:TrainingWeek? = null
    var week4:TrainingWeek? = null

    private var trWeekList = listOf<TrainingWeek>()


    var currentTrainingDay = TrainingDay()
    var weeksDaysExercisesMap = mutableMapOf<TrainingWeek, Map<TrainingDay, List<Exercise>>>()


    suspend fun loadData(templateId: Long, repository: Repository) {
        ioScope.launch {
            trainingWeek = repository.getWeeksForCurrentTemplate(templateId)
        }
    }

    fun getTrainingTemplate(key: Long, repository: Repository): TrainingTemplate {
        val template = ioScope.async { repository.getTemplate(key) }
        ioScope.launch {
            templateEntity = template.await()
        }
        return templateEntity
    }

    fun getTrainingWeeks(key: Long, repository: Repository): List<TrainingWeek> {
        val list = ioScope.async { repository.returnWeeksList(key) }
        ioScope.launch { trWeekList = list.await() }
        return trWeekList
    }

    fun returnWeek(): TrainingWeek {
        return trainingWeek
    }


    //укладывает данные в коллекцию
    fun packDataAtMap() {
        for (element in weeksList) {
            val weekNumber = element.weekNumber
            weeksDaysExercisesMap.put(
                returnSpecificWeek(weekNumber),
                returnMapDayExerciseList(weekNumber)
            )
        }
    }

    //возвращает неделю из списка в зависимости от номера недели
    private fun returnSpecificWeek(weekNumber: Int): TrainingWeek {
        return weeksList.get(weekNumber - 1)
    }

    //возвращает коллекцию из пар тренировочный день - список упражнений в зависимости от номера недели
    private fun returnMapDayExerciseList(weekNumber: Int): Map<TrainingDay, List<Exercise>> {
        val daysExercisesMap = mutableMapOf<TrainingDay, List<Exercise>>()
        for (element in trainingDayList) {
            if (element.weekNumber == weekNumber) {
                val dayNumber = element.dayNumber
                daysExercisesMap.put(
                    element,
                    returnExerciseListForSpecificDay(weekNumber, dayNumber)
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

    fun createTrainingTemplate(name: String, description: String, numberOfWeeks: Int) {
        val newTemplate = TrainingTemplate()
        newTemplate.templateName = name
        newTemplate.templateDescription = description
        newTemplate.numberOfTrainingWeeks = numberOfWeeks
        saveNewTrainingTemplate(newTemplate)
    }

//    fun createTrainingWeek(weekNumber: Int){
//        val newWeek = TrainingWeek()
//        newWeek.weekNumber = weekNumber
//        weeksList.add(newWeek)
////        trainingWeek = newWeek
//    }

    fun createTrainingWeek(weekNumber: Int) {
        val newWeek = TrainingWeek()
        newWeek.weekNumber = weekNumber
        when (weekNumber) {
            1 -> week1 = newWeek
            2 -> week2 = newWeek
            3 -> week3 = newWeek
            4 -> week4 = newWeek
        }
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
        putToExercisesList(newExercise)
    }

    private fun putToExercisesList(exercise: Exercise) {
        exercisesList.add(exercise)
    }

    private fun saveTrainingDay(day: TrainingDay) {
        trainingDayList.add(day)
    }


    private fun saveNewTrainingTemplate(template: TrainingTemplate) {
        templateEntity = template
    }


    fun returnTemplateEntity(): TrainingTemplate {
        return templateEntity
    }

    fun returnWeeksList(): MutableList<TrainingWeek> {
        return weeksList
    }


    //очищает все данные в TemporaryDataStorage
    fun clearAllData() {
        weeksDaysExercisesMap.clear()
        exercisesList.clear()
        trainingDayList.clear()
        weeksList.clear()
    }

    fun returnExerciseLiveDataList(
        weekNumber: Int,
        dayNumber: Int
    ): MutableLiveData<List<Exercise>> {
        exercisesLiveDataList.value =
            exercisesList.filter { it.weekNumber == weekNumber && it.dayNumber == dayNumber }
        return exercisesLiveDataList
    }
}