package com.example.android.creatingtrainingday

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass


class ExerciseListViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val repository: Repository

    init {
        repository = Repository(dataSource)
    }

    val database = dataSource

    var temporaryExercises = database.temporaryExerciseDao.getAllExercises()

    private val temporaryDataStorage = TemporaryDataStorageClass.instance
    var weekDayAndNumber: String = ""


    fun getText() {
        val day = temporaryDataStorage.trainingDay
        weekDayAndNumber = "Week number:${day.weekNumber}, Week day:${day.dayOfTheWeek} "
    }

}