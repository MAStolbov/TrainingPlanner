package com.example.android.trainigtemplate

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import com.example.android.database.templateEntityDao.TrainingTemplate

class TrainingTemplatesListViewModel(dataSource: TemplatesDatabaseDAO, application: Application):ViewModel() {

    val database = dataSource

    private var _navigateToCreatingTemplate = MutableLiveData<Boolean>()
    val navigateToCreatingTemplate
        get() = _navigateToCreatingTemplate

    fun createTemplate() {
        val newTemplate = TrainingTemplate()
        database.insertTemplate(newTemplate)
        _navigateToCreatingTemplate.value = true
    }

    fun doneNavigation() {
        _navigateToCreatingTemplate.value = false
    }
}

