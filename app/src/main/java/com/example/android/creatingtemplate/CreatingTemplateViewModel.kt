package com.example.android.creatingtemplate

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingweekEntityDao.TrainingWeek


class CreatingTemplateViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    val database = dataSource
    private var clicksCount: Int = 0
    private var newTemplateId: Long = 0

    private val _addWeek = MutableLiveData<Int>()
    val addWeek: LiveData<Int>
        get() = _addWeek

    private val _navigationToCreatingTrainingDay = MutableLiveData<Boolean>()
    val navigationToCreatingTrainingDay: LiveData<Boolean>
        get() = _navigationToCreatingTrainingDay


    fun createTemplate(name: String, description: String) {
        val newTemplate = TrainingTemplate()
        getNewTemplateId()
        newTemplate.templateId = newTemplateId
        newTemplate.templateName = name
        newTemplate.templateDescription = description
        newTemplate.numberOfTrainingWeeks = clicksCount
        database.templateDatabaseDao.insertTemplate(newTemplate)
    }

    fun addWeek() {
        clicksCount += 1
        val newWeek = TrainingWeek()
        newWeek.weekNumber = clicksCount
        newWeek.parentTemplateId = newTemplateId
        database.trainingWeek.insertWeek(newWeek)
        _addWeek.value = clicksCount
    }

    fun addTrainingDay() {
        _navigationToCreatingTrainingDay.value = true
    }


    /**
     * Generate new ID for template
     */
    private fun getNewTemplateId() {
        val previousTemplate = database.templateDatabaseDao.getLastTemplate()
        if (previousTemplate == null) {
            newTemplateId = 1
        } else {
            newTemplateId = previousTemplate.templateId + 1
        }
    }

}