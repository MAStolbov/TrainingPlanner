package com.example.android.creatingtrainingday

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.util.TrainingWeekData


class CreatingTrainingDayViewModel(dataSours: TemplatesDatabase, application: Application) :
    ViewModel() {

    val database = dataSours

    var temporaryExercises = database.temporaryExerciseDao.getAllExercises()

    private var newDayId:Long = 0

    private var weekId: Long = 0
    private var weekNumber: Int = 0
    private var dayOfTheWeek: String = ""
    var weekDayAndNumber: String = ""

    fun getWeekNumber() {
        weekNumber = TrainingWeekData.returnWeekNumber()
    }


    fun getWeekId() {
        weekId = TrainingWeekData.returnWeekId(weekNumber)
    }

    fun getDayOfTheWeek(){
        dayOfTheWeek = TrainingWeekData.returnDayOfTheWeek()
    }

    fun setText() {
        weekDayAndNumber = TrainingWeekData.returnDayAndWeekNumber()
    }

    fun createNewTrainingDay(){
        val newDay = TrainingDay()
        getNewDayId()
        newDay.dayId = newDayId
        newDay.parentWeekId = weekId
        newDay.dayOfTheWeek = dayOfTheWeek
    }

    private fun getNewDayId() {
        val previosDayId = database.trainingDayDao.getDayMaxId()
        if (previosDayId == null) {
            newDayId = 1
        } else {
            newDayId = previosDayId + 1
        }

    }

}