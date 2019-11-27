package com.example.android.creatingtemplate

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import com.example.android.database.templateEntityDao.TrainingTemplate


class CreatingTemplateViewModel(dataSource: TemplatesDatabaseDAO, application: Application) :
    ViewModel() {

    val database = dataSource
    var clicksCount: Int = 0

    private var _maxWeeksAdd = MutableLiveData<Boolean>()
    val maxWeeksAdd: LiveData<Boolean>
        get() = _maxWeeksAdd

    fun createTemplate(name: String, description: String) {
        val newTemplate = TrainingTemplate()
        newTemplate.templateName = name
        newTemplate.templateDescription = description
        newTemplate.numberOfTrainingWeeks = clicksCount
        database.insertTemplate(newTemplate)
    }

    fun addWeek() {
        if (clicksCount < 4){
            clicksCount += 1
        }else{
            _maxWeeksAdd.value = true
        }
    }
}