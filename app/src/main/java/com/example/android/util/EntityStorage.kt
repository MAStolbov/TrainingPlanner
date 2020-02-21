package com.example.android.util



import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.database.trainingweekEntityDao.TrainingWeek

object EntityStorage {

    private lateinit var templateEntity:TrainingTemplate
    var trainingWeek1 = TrainingWeek()
    var trainingWeek2 = TrainingWeek()
    var trainingWeek3 = TrainingWeek()
    var trainingWeek4 = TrainingWeek()
    var trainingDay = TrainingDay()
    var exercisesList = mutableListOf<Exercise>()

    var weeksDaysExercisesMap = mutableMapOf<TrainingWeek,Map<TrainingDay,MutableList<Exercise>>>()
    var daysExercisesMap = mutableMapOf<TrainingDay,MutableList<Exercise>>()



    fun putToWeeksDaysExerciseMap(){
        val map1 = daysExercisesMap.filter {
            it.key.weekNumber == 1
        }
        weeksDaysExercisesMap.put(trainingWeek1,map1)

        val map2 = daysExercisesMap.filter {
            it.key.weekNumber == 2
        }
        weeksDaysExercisesMap.put(trainingWeek2,map2)

        val map3 = daysExercisesMap.filter {
            it.key.weekNumber == 3
        }
        weeksDaysExercisesMap.put(trainingWeek3,map3)

        val map4 = daysExercisesMap.filter {
            it.key.weekNumber == 4
        }
        weeksDaysExercisesMap.put(trainingWeek4,map4)
    }


    fun putToDaysExercisesMap(){
        daysExercisesMap.put(trainingDay, exercisesList)
        exercisesList.clear()
    }

    fun putToExercisesList(exercise:Exercise){
        exercisesList.add(exercise)
    }

    fun saveTrainingDay(day:TrainingDay){
        trainingDay = day
    }


    fun addNewTemplateEntity(template: TrainingTemplate){
        templateEntity = template
    }


    fun returnTemplateEntity():TrainingTemplate{
        return templateEntity
    }



    fun clearAllMaps(){
        daysExercisesMap.clear()
        weeksDaysExercisesMap.clear()
        exercisesList.clear()
    }


}