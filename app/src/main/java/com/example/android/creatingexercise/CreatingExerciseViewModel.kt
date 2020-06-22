package com.example.android.creatingexercise


import androidx.lifecycle.ViewModel
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass

class CreatingExerciseViewModel : ViewModel() {

    private val temporaryDataStorage = TemporaryDataStorageClass.instance
    private var newExerciseName: String = ""
    private var newSets: String = ""
    private var newReps: String = ""
    private var newWeight: String = ""

    fun getDayAndWeekNumberText() =
        "Week number:${temporaryDataStorage.currentTrainingDay.weekNumber}, Week day:${temporaryDataStorage.currentTrainingDay.dayOfTheWeek} "


    fun getExerciseInfo(name: String, sets: String, reps: String, weight: String) {
        newExerciseName = name
        newSets = sets
        newReps = reps
        newWeight = weight
    }


    fun createNewExercise() {
        temporaryDataStorage.createNewExercise(newExerciseName, newSets, newReps, newWeight)
    }

}