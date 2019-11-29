package com.example.android.creatingtemplate

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingweekEntityDao.TrainingWeek


class CreatingTemplateViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    val database = dataSource
    private var clicksCount: Int = 0


    private var _maxWeeksAdd = MutableLiveData<Boolean>()
    val maxWeeksAdd: LiveData<Boolean>
        get() = _maxWeeksAdd

    fun createTemplate(name: String, description: String) {
        val newTemplate = database.templateDatabaseDao.getLastTemplate()
        newTemplate.templateName = name
        newTemplate.templateDescription = description
        newTemplate.numberOfTrainingWeeks = clicksCount
        database.templateDatabaseDao.updateTemplate(newTemplate)
    }

    fun addWeek() {
        if (clicksCount < 4){
            clicksCount += 1
            val newWeek = TrainingWeek()
            val template = database.templateDatabaseDao.getLastTemplate()
            newWeek.weekNumber = clicksCount
            newWeek.parentTemplateId = template.templateId
            database.trainingWeek.insertWeek(newWeek)
        }else{
            _maxWeeksAdd.value = true
        }
    }
}