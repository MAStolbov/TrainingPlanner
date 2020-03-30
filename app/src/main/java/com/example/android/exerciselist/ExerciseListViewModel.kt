package com.example.android.exerciselist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass
import com.example.android.util.Util


class ExerciseListViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val temporaryDataStorage = TemporaryDataStorageClass.instance
    private val repository: Repository

    init {
        repository = Repository(dataSource)
    }

    val database = dataSource

    var temporaryExercises:MutableLiveData<List<Exercise>> = temporaryDataStorage.returnExerciseLiveDataList(Util.returnWeekNumber(),Util.returnDayNumber())

    var weekDayAndNumber: String = ""


    fun getText() {
        weekDayAndNumber = "Week number:${Util.weekNumber}, Week day:${Util.dayOfTheWeek} "
    }

}