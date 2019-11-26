package com.example.android.creatingtemplate

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import com.example.android.database.templateEntityDao.TrainingTemplate


class CreatingTemplateViewModel(dataSource: TemplatesDatabaseDAO, application: Application) : ViewModel(){

    val database = dataSource

    fun createTemplate(name:String){
        val newTemplate = TrainingTemplate()
        newTemplate.templateName = name
        database.insertTemplate(newTemplate)
    }
}