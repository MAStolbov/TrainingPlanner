package com.example.android.trainigtemplate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import com.example.android.database.templateEntityDao.TrainingTemplate

class TrainingTemplatesListViewModel(dataSource: TemplatesDatabaseDAO, application: Application):
    AndroidViewModel(application) {

    val database = dataSource
    val templates = database.getAllTemplates()

    fun deleteTemplate(id:Int){
        database.deleteTemplate(id)
    }

    fun newTemplate(){
        val newTemplate = TrainingTemplate()
        database.insertTemplate(newTemplate)
    }
}

