package com.example.android.creatingexercise

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.idStorageEntityDao.IdStorageEntity
import com.example.android.database.temporaryEntity.TemporaryExercise
import com.example.android.repository.Repository
import com.example.android.util.EntityStorage
import com.example.android.util.TrainingWeekData

class CreatingExerciseViewModel (dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val repository: Repository
    init {
        repository = Repository(dataSource)
    }

    val database = dataSource
    var textWithDayAndNumberOfWeek:String = ""
    private var newExerciseId:Long = 0
    private var parentDayId:Long = 0
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
        newExercise.parentTrainingDayId = parentDayId
        newExercise.exerciseName = newExerciseName
        newExercise.set = newSets
        newExercise.rep = newReps
        newExercise.weight = newWeight
        EntityStorage.addNewExerciseEntityInMap(newExercise)
        putExerciseIdInStorage()
    }

    private fun putExerciseIdInStorage(){
        val idStorage = IdStorageEntity()
        idStorage.exerciseId = newExerciseId
        repository.insertIdStorage(idStorage)
    }

    fun getParentDayId(){
        parentDayId = TrainingWeekData.returnDayId()
    }

    private fun getNewExerciseId(){
        val previousExerciseId = repository.returnMaxExerciseId()
        if (previousExerciseId == null){
            newExerciseId = 1
        }else{
            newExerciseId = previousExerciseId + 1
        }
    }
}