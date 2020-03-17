package com.example.android.util


import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.database.trainingweekEntityDao.TrainingWeek

object TemporaryDataStorage {

    private lateinit var templateEntity: TrainingTemplate
    var trainingDay = TrainingDay()
    var weeksList = mutableListOf<TrainingWeek>()
    var exercisesList = mutableListOf<Exercise>()
    var weeksDaysExercisesMap = mutableMapOf<TrainingWeek, Map<TrainingDay, MutableList<Exercise>>>()
    var daysExercisesMap = mutableMapOf<TrainingDay, MutableList<Exercise>>()


    //укладывает данные в коллекцию
    fun putAtWeeksDaysExerciseMap() {
        for (element in weeksList) {
            val weekNumber = element.weekNumber
            weeksDaysExercisesMap.put(
                returnSpecificWeek(weekNumber),
                returnSpecificPairDayExercises(weekNumber)
            )
        }
    }

    //возвращает неделю из списка в завимсимости от номера недели
    private fun returnSpecificWeek(weekNumber: Int): TrainingWeek {
        return weeksList.get(weekNumber - 1)
    }

    //возвращает пару день - список упражнений в завимсимости от номера недели
    private fun returnSpecificPairDayExercises(weekNumber: Int): Map<TrainingDay, MutableList<Exercise>> {
        return daysExercisesMap.filter {
            it.key.weekNumber == weekNumber
        }
    }

    fun putToDaysExercisesMap() {
        daysExercisesMap.put(trainingDay, exercisesList)
        exercisesList.clear()
    }

    fun putToExercisesList(exercise: Exercise) {
        exercisesList.add(exercise)
    }

    fun saveTrainingDay(day: TrainingDay) {
        trainingDay = day
    }


    fun addNewTemplateEntity(template: TrainingTemplate) {
        templateEntity = template
    }


    fun returnTemplateEntity(): TrainingTemplate {
        return templateEntity
    }


    //очищает все данные в TemporaryDataStorage
    fun clearAllData() {
        daysExercisesMap.clear()
        weeksDaysExercisesMap.clear()
        exercisesList.clear()
        weeksList.clear()
    }


}