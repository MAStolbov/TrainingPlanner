package com.example.android.util

import androidx.lifecycle.MutableLiveData
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.database.trainingweekEntityDao.TrainingWeek

class TemporaryDataStorageClass private constructor() {
    private object Holder {
        val INSTANCE = TemporaryDataStorageClass()
    }

    companion object {
        val instance = Holder.INSTANCE
    }


    private lateinit var templateEntity: TrainingTemplate
    var trainingDayList = mutableListOf<TrainingDay>()
    var weeksList = mutableListOf<TrainingWeek>()
    var exercisesList = mutableListOf<Exercise>()
    var weeksDaysExercisesMap = mutableMapOf<TrainingWeek, Map<TrainingDay, List<Exercise>>>()
    var exercisesLiveDataList = MutableLiveData<List<Exercise>>()
    var currentTrainingDay = TrainingDay()


    //укладывает данные в коллекцию
    fun packDataAtMap() {
        for (element in weeksList) {
            val weekNumber = element.weekNumber
            weeksDaysExercisesMap.put(returnSpecificWeek(weekNumber), returnMapDayExerciseList(weekNumber))
        }
    }

    //возвращает неделю из списка в зависимости от номера недели
    private fun returnSpecificWeek(weekNumber: Int): TrainingWeek {
        return weeksList.get(weekNumber - 1)
    }

    //возвращает коллекцию из пар тренировочный день - список упражнений в зависимости от номера недели
    private fun returnMapDayExerciseList(weekNumber: Int): Map<TrainingDay, List<Exercise>> {
        val daysExercisesMap = mutableMapOf<TrainingDay, List<Exercise>>()
        for (element in trainingDayList){
            if (element.weekNumber == weekNumber){
                val dayNumber = element.dayNumber
                daysExercisesMap.put(element,returnExerciseListForSpecificDay(weekNumber,dayNumber))
            }
        }
        return daysExercisesMap
    }

    //возращает список упражнений для конкретного тренировчного дня
    private fun returnExerciseListForSpecificDay(weekNumber: Int, dayNumber:Int): List<Exercise> {
        return exercisesList.filter { it.weekNumber == weekNumber && it.dayNumber == dayNumber}
    }


    //получает из списка Тренировочный день в зависимости от номера недели и дня
    private fun setCurrentTrainingDay(weekNumber: Int, dayNumber: Int){
        currentTrainingDay = trainingDayList.single{it.weekNumber == weekNumber && it.dayNumber == dayNumber}
    }

    fun createTrainingTemplate(name: String, description: String, numberOfWeeks:Int) {
        val newTemplate = TrainingTemplate()
        newTemplate.templateName = name
        newTemplate.templateDescription = description
        newTemplate.numberOfTrainingWeeks = numberOfWeeks
        saveNewTrainingTemplate(newTemplate)
    }

    fun createTrainingWeek(weekNumber: Int){
        val newWeek = TrainingWeek()
        newWeek.weekNumber = weekNumber
        weeksList.add(newWeek)
    }

    fun createTrainingDay(weekNumber: Int, dayNumber: Int) {
        //проверка существует ли уже такой день в коллекции
        val checkDay = trainingDayList.find { it.weekNumber == weekNumber && it.dayNumber == dayNumber }
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


    //очищает все данные в TemporaryDataStorage
    fun clearAllData() {
        weeksDaysExercisesMap.clear()
        exercisesList.clear()
        trainingDayList.clear()
        weeksList.clear()
    }

    fun returnExerciseLiveDataList(weekNumber:Int,dayNumber:Int):MutableLiveData<List<Exercise>>{
       exercisesLiveDataList.value = exercisesList.filter { it.weekNumber == weekNumber && it.dayNumber == dayNumber }
        return exercisesLiveDataList
    }
}