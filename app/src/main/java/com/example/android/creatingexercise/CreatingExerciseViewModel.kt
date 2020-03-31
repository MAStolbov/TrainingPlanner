package com.example.android.creatingexercise

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass
import com.example.android.util.Util

class CreatingExerciseViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val repository: Repository

    init {
        repository = Repository(dataSource)
    }

    private val temporaryDataStorage = TemporaryDataStorageClass.instance
    val database = dataSource
    var textWithDayAndNumberOfWeek: String = ""
    var newExerciseName: String = ""
    var newSets: String = ""
    var newReps: String = ""
    var newWeight: String = ""
    private val currentTrainingDay = temporaryDataStorage.currentTrainingDay

    fun getDayAndWeekNumberText() {
        textWithDayAndNumberOfWeek =
            "Week number:${currentTrainingDay.weekNumber}, Week day:${currentTrainingDay.dayOfTheWeek} "
    }

    fun getExerciseInfo(name: String, sets: String, reps: String, weight: String) {
        newExerciseName = name
        newSets = sets
        newReps = reps
        newWeight = weight
    }


    fun createNewExercise() {
        temporaryDataStorage.createNewExercise(newExerciseName,newSets,newReps,newWeight)
    }

}