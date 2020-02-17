package com.example.android.creatingtrainingday

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.idStorageEntityDao.IdStorageEntity
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.repository.Repository
import com.example.android.util.EntityStorage
import com.example.android.util.TrainingWeekData


class CreatingTrainingDayViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val repository: Repository
    init {
        repository = Repository(dataSource)
    }

    val database = dataSource

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

    fun getText() {
        weekDayAndNumber = TrainingWeekData.returnDayAndWeekNumber()
    }

    fun createNewTrainingDay(){
        val newDay = TrainingDay()
        getNewDayId()
        newDay.dayId = newDayId
        newDay.parentWeekId = weekId
        newDay.dayOfTheWeek = dayOfTheWeek
        EntityStorage.addNewDayEntityInMap(newDay)
        putDayIdInStorage()
    }

    private fun putDayIdInStorage(){
        val idStorage = IdStorageEntity()
        idStorage.dayId = newDayId
        repository.insertIdStorage(idStorage)
    }

    fun saveDayId(){
        TrainingWeekData.sendDayId(newDayId)
    }

    private fun getNewDayId() {
        val previousDayId = repository.returnMaxDayId()
        if (previousDayId == null) {
            newDayId = 1
        } else {
            newDayId = previousDayId + 1
        }

    }

}