package com.example.android.util



import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.database.trainingweekEntityDao.TrainingWeek

object EntityStorage {

    private var newKeyForMap:Int = 0

    private lateinit var templateEntity:TrainingTemplate
    private var weekEntityMap = mutableMapOf<Int, TrainingWeek>()
    private var dayEntityMap = mutableMapOf<Int, TrainingDay>()
    private var exerciseEntityMap = mutableMapOf<Int, Exercise>()


    fun addNewTemplateEntity(template: TrainingTemplate){
        templateEntity = template
    }

    fun addNewWeekEntityMap(weekMap: MutableMap<Int, TrainingWeek>){
        weekEntityMap = weekMap
    }

    fun addNewDayEntityInMap(dayEntity:TrainingDay){
        dayEntityMap.put(generateNewKeyForMap(),dayEntity)
    }

    fun addNewExerciseEntityInMap(exerciseEntity:Exercise){
        exerciseEntityMap.put(generateNewKeyForMap(),exerciseEntity)
    }

    fun returnTemplateEntity():TrainingTemplate{
        return templateEntity
    }

    fun returnWeekEntityMap():MutableMap<Int, TrainingWeek>{
        return  weekEntityMap
    }
    fun returnDayEntityMap():MutableMap<Int, TrainingDay>{
        return  dayEntityMap
    }
    fun returnExerciseEntityMap():MutableMap<Int, Exercise>{
        return  exerciseEntityMap
    }

    fun clearAllMaps(){
        weekEntityMap.clear()
        dayEntityMap.clear()
        exerciseEntityMap.clear()
    }

    fun generateNewKeyForMap():Int{
        newKeyForMap += 1
        return newKeyForMap
    }


}