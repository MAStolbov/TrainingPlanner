package com.example.android.creatingexercise

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.temporaryEntity.TemporaryExercise
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorage
import com.example.android.util.TemporaryDataStorageClass
import com.example.android.util.TrainingWeekData

class CreatingExerciseViewModel (dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val repository: Repository
    init {
        repository = Repository(dataSource)
    }

    val database = dataSource
    var textWithDayAndNumberOfWeek:String = ""
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
        newExercise.exerciseName = newExerciseName
        newExercise.set = newSets
        newExercise.rep = newReps
        newExercise.weight = newWeight
        TemporaryDataStorage.putToExercisesList(newExercise)
    }

}