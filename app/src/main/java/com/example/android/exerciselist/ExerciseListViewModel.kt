package com.example.android.exerciselist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.util.TemporaryDataStorageClass


class ExerciseListViewModel : ViewModel() {

    private val temporaryDataStorage = TemporaryDataStorageClass.instance


    var temporaryExercises: MutableLiveData<List<Exercise>> =
        temporaryDataStorage.returnExerciseLiveDataList()

    fun getText() =
        "Week number:${temporaryDataStorage.currentTrainingDay.weekNumber}, Week day:${temporaryDataStorage.currentTrainingDay.dayOfTheWeek} "

}