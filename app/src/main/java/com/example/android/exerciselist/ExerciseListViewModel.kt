package com.example.android.exerciselist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass


class ExerciseListViewModel(private val repository: Repository) : ViewModel() {

    private val temporaryDataStorage = TemporaryDataStorageClass.instance


    var temporaryExercises: MutableLiveData<List<Exercise>> =
        temporaryDataStorage.returnExerciseLiveDataList()

    fun getText() =
        "Week number:${temporaryDataStorage.currentTrainingDay.weekNumber}, Week day:${temporaryDataStorage.currentTrainingDay.dayOfTheWeek} "


    fun deleteExercisesForCurrentDay(){
        temporaryDataStorage.deleteDayExercises(repository)
        temporaryExercises = temporaryDataStorage.returnExerciseLiveDataList()
    }

    fun deleteCurrentDay(){
        temporaryDataStorage.deleteCurrentDay(repository)
        temporaryDataStorage.deleteDayExercises(repository)
    }
}