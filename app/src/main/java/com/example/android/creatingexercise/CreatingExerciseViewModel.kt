package com.example.android.creatingexercise

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.temporaryEntity.TemporaryExercise
import com.example.android.util.TrainingWeekData

class CreatingExerciseViewModel (dataSours: TemplatesDatabase, application: Application) :
    ViewModel() {

    val database = dataSours
    var textWithDayAndNumberOfWeek:String = ""
    private var newExerciseId:Long = 0
    var newExerciseName:String = ""
    var newSets:String = ""
    var newReps:String = ""
    var newWeight:String = ""

    fun getDayAndWeekNumberText(){
        textWithDayAndNumberOfWeek = TrainingWeekData.returnDayAndWeekNumber()
    }

    fun getExerciseInfo(name:String,sets:String,reps:String,weight:String){
        newExerciseName = name
        newSets = sets
        newReps = reps
        newWeight = weight
    }

    fun createTemporaryExercise(){
        val newTemporaryExercise = TemporaryExercise()
        newTemporaryExercise.exerciseName = newExerciseName
        newTemporaryExercise.set = newSets
        newTemporaryExercise.rep = newReps
        newTemporaryExercise.weight = newWeight
        database.temporaryExerciseDao.insertExercise(newTemporaryExercise)
    }

    fun createNewExercise(){
        val newExercise = Exercise()
        getNewExerciseId()
        newExercise.exerciseId = newExerciseId
    }

    private fun getNewExerciseId(){
        val previousExerciseId = database.exerciseDao.getExerciseMaxId()
        if (previousExerciseId == null){
            newExerciseId = 1
        }else{
            newExerciseId = previousExerciseId + 1
        }
    }
}