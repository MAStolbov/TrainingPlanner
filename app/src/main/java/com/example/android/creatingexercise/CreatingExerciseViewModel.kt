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

    fun getDayAndWeekNumberText() {
        textWithDayAndNumberOfWeek =
            "Week number:${Util.weekNumber}, Week day:${Util.dayOfTheWeek} "
    }

    fun getExerciseInfo(name: String, sets: String, reps: String, weight: String) {
        newExerciseName = name
        newSets = sets
        newReps = reps
        newWeight = weight
    }


    fun createNewExercise() {
        val newExercise = Exercise()
        newExercise.exerciseName = newExerciseName
        newExercise.set = newSets
        newExercise.rep = newReps
        newExercise.weight = newWeight
        newExercise.weekNumber = Util.returnWeekNumber()
        newExercise.dayNumber = Util.returnDayNumber()
        temporaryDataStorage.putToExercisesList(newExercise)
    }

}