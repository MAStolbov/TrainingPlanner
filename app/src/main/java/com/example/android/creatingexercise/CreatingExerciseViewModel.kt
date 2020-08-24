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


    fun getExercise(weekNumber:Int,dayNumber:Int,name:String){
        exercise = temporaryDataStorage.returnExerciseForRedaction(weekNumber, dayNumber, name)
    }

    fun updateExercise(name: String, sets: String, reps: String, weight: String){
        exercise.apply {
            this.exerciseName = name
            this.set= sets
            this.rep = reps
            this.weight = weight
        }
        temporaryDataStorage.addExerciseAtList(exercise)
    }

    fun createNewExercise(name: String, sets: String, reps: String, weight: String) {
        temporaryDataStorage.createNewExercise(name, sets, reps, weight)
    }

}