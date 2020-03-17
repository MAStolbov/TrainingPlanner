package com.example.android.creatingtrainingday

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorage
import com.example.android.util.TrainingWeekData


class CreatingTrainingDayViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val repository: Repository

    init {
        repository = Repository(dataSource)
    }

    val database = dataSource

    var temporaryExercises = database.temporaryExerciseDao.getAllExercises()

    private var weekNumber: Int = 0
    private var dayOfTheWeek: String = ""
    var weekDayAndNumber: String = ""

    fun getWeekNumber() {
        weekNumber = TrainingWeekData.returnWeekNumber()
    }


    fun getDayOfTheWeek() {
        dayOfTheWeek = TrainingWeekData.returnDayOfTheWeek()
    }

    fun getText() {
        weekDayAndNumber = TrainingWeekData.returnDayAndWeekNumber()
    }

    fun createNewTrainingDay() {
        val newDay = TrainingDay()
        newDay.dayOfTheWeek = dayOfTheWeek
        newDay.weekNumber = weekNumber
        TemporaryDataStorage.saveTrainingDay(newDay)
    }


}