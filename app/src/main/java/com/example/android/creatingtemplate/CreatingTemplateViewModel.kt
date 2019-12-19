package com.example.android.creatingtemplate

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.util.SelectedTrainingDaysAndWeeks
import com.example.android.util.TemplateNameAndDescription
import com.example.android.database.TemplatesDatabase
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingweekEntityDao.TrainingWeek
import com.example.android.util.TrainingWeekData


class CreatingTemplateViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {


    val database = dataSource
    private var clicksCount: Int = 0
    private var newTemplateId: Long = 0
    private var newWeekId: Long = 0

    private var firstWeekId:Long = 0
    private var secondWeekId:Long = 0
    private var thirdWeekId:Long = 0
    private var fourthWeekId:Long = 0


    //private var weeks: MutableMap<Int, MutableMap<Int, Boolean>>

    private var week1= initMap()
    private var week2= initMap()
    private var week3 = initMap()
    private var week4 = initMap()

    private fun initMap(): MutableMap<Int, Boolean> {
        return mutableMapOf(
            1 to false, 2 to false, 3 to false,
            4 to false, 5 to false, 6 to false, 7 to false
        )
    }

    private val _addNewWeek = MutableLiveData<Int>()
    val addNewWeek: LiveData<Int>
        get() = _addNewWeek

    private val _maxWeek = MutableLiveData<Boolean>()
    val maxWeek: LiveData<Boolean>
        get() = _maxWeek


//    private val _nextStepOfTrainingTemplateCreating = MutableLiveData<Boolean>()
//    val nextStepOfTrainingTemplateCreating: LiveData<Boolean>
//        get() =_nextStepOfTrainingTemplateCreating


    fun createTemplate(name: String, description: String) {
        val newTemplate = TrainingTemplate()
        newTemplate.templateId = newTemplateId
        newTemplate.templateName = name
        newTemplate.templateDescription = description
        newTemplate.numberOfTrainingWeeks = clicksCount
        database.templateDatabaseDao.insertTemplate(newTemplate)
        TemplateNameAndDescription.templateName = name
        TemplateNameAndDescription.templateDescription = description
    }

    fun addWeek() {
        if (clicksCount < 4) {
            clicksCount += 1
            val newWeek = TrainingWeek()
            getNewWeekId()
            newWeek.weekId = newWeekId
            newWeek.weekNumber = clicksCount
            newWeek.parentTemplateId = newTemplateId
            database.trainingWeekDao.insertWeek(newWeek)
            saveWeeksId(clicksCount,newWeekId)
            _addNewWeek.value = clicksCount
        } else {
            _maxWeek.value = true
        }
    }


//    fun nextStep() {
//        _nextStepOfTrainingTemplateCreating.value = true
//    }


    fun selectFirstWeekTrainingDay(dayNumber: Int) {
        week1[dayNumber] = week1[dayNumber] != true
    }

    fun selectSecondWeekTrainingDay(dayNumber: Int) {
        week2[dayNumber] = week2[dayNumber] != true
    }

    fun selectThirdWeekTrainingDay(dayNumber: Int) {
        week3[dayNumber] = week3[dayNumber] != true
    }

    fun selectFourthWeekTrainingDay(dayNumber: Int) {
        week4[dayNumber] = week4[dayNumber] != true
    }


    fun sendSelectedTrainingDays() {
        SelectedTrainingDaysAndWeeks.sendSelectedDays(week1, week2, week3, week4)
    }

    fun sendNumberOfWeeks() {
        SelectedTrainingDaysAndWeeks.sendNumberOfWeeks(clicksCount)
    }

    fun sendWeekId(){
        TrainingWeekData.sendWeeksId(firstWeekId,secondWeekId,thirdWeekId,fourthWeekId)
    }

    private fun saveWeeksId(weekNumber:Int, weekId:Long){
        when(weekNumber){
            1 -> firstWeekId = weekId
            2 -> secondWeekId = weekId
            3 -> thirdWeekId = weekId
            4 -> fourthWeekId = weekId
        }
    }


    /**
     * Generate new ID for template
     */
    fun getNewTemplateId() {
        val previousTemplateID = database.templateDatabaseDao.getTemplateMaxId()
        if (previousTemplateID == null) {
            newTemplateId = 1
        } else {
            newTemplateId = previousTemplateID + 1
        }
    }

    /**
     * Generate new ID for week
     */
    private fun getNewWeekId() {
        val previosWeekId = database.trainingWeekDao.getWeekMaxId()
        if (previosWeekId == null) {
            newWeekId = 1
        } else {
            newWeekId = previosWeekId + 1
        }

    }

}