package com.example.android.creatingtemplate

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.util.SelectedTrainingDays
import com.example.android.util.TemplateNameAndDescription
import com.example.android.database.TemplatesDatabase
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingweekEntityDao.TrainingWeek


class CreatingTemplateViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    val database = dataSource
    private var clicksCount: Int = 0
    private var newTemplateId: Long = 0


    //private var weeks: MutableMap<Int, MutableMap<Int, Boolean>>

    private var week1: MutableMap<Int, Boolean> = mutableMapOf(1 to false, 2 to false,3 to false,
        4 to false, 5 to false,6 to false,7 to false)
//    private var week2: MutableMap<Int, Boolean>
//    private var week3: MutableMap<Int, Boolean>

    private val _addWeek = MutableLiveData<Int>()
    val addWeek: LiveData<Int>
        get() = _addWeek



//    private val _nextStepOfTrainingTemplateCreating = MutableLiveData<Boolean>()
//    val nextStepOfTrainingTemplateCreating: LiveData<Boolean>
//        get() =_nextStepOfTrainingTemplateCreating


    fun createTemplate(name: String, description: String) {
        val newTemplate = TrainingTemplate()
        getNewTemplateId()
        newTemplate.templateId = newTemplateId
        newTemplate.templateName = name
        newTemplate.templateDescription = description
        newTemplate.numberOfTrainingWeeks = clicksCount
        database.templateDatabaseDao.insertTemplate(newTemplate)
        TemplateNameAndDescription.templateName = name
    }

    fun addWeek() {
        clicksCount += 1
        val newWeek = TrainingWeek()
        newWeek.weekNumber = clicksCount
        newWeek.parentTemplateId = newTemplateId
        database.trainingWeek.insertWeek(newWeek)
        _addWeek.value = clicksCount
    }


//    fun nextStep() {
//        _nextStepOfTrainingTemplateCreating.value = true
//    }


    fun selectFirstWeekTrainingDay(dayNumber: Int) {
        week1[dayNumber] = true
    }


    fun sendSelectedTrainingDays(){
        SelectedTrainingDays.sendFirstTrainingWeek(week1)

    }


    /**
     * Generate new ID for template
     */
    private fun getNewTemplateId() {
        val previousTemplateID = database.templateDatabaseDao.getTemplateMaxId()
        if (previousTemplateID == null) {
            newTemplateId = 1
        } else {
            newTemplateId = previousTemplateID + 1
        }
    }

}