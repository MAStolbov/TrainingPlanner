package com.example.android.creatingexercise


import androidx.lifecycle.ViewModel
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass

class CreatingExerciseViewModel : ViewModel() {

    var exercise = Exercise()

    private val temporaryDataStorage = TemporaryDataStorageClass.instance

    fun getDayAndWeekNumberText() =
        "Week number:${temporaryDataStorage.currentTrainingDay.weekNumber}, Week day:${temporaryDataStorage.currentTrainingDay.dayOfTheWeek} "


    fun getExercise(localId:Int) {
        exercise = temporaryDataStorage.returnExerciseForRedaction(localId)
    }

    fun updateExercise(name: String, sets: String, reps: String, weight: String) {
        temporaryDataStorage.exercisesList.single { it.localId == exercise.localId }
            .apply {
                this.exerciseName = name
                this.set = sets
                this.rep = reps
                this.weight = weight
            }
    }


    fun createNewExercise(name: String, sets: String, reps: String, weight: String) {
        temporaryDataStorage.createNewExercise(name, sets, reps, weight)
    }

}