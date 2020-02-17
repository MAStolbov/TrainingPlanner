package com.example.android.trainigtemplate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.repository.Repository

class TrainingTemplatesListViewModel(dataSource: TemplatesDatabase, application: Application) :
    AndroidViewModel(application) {

    private val repository:Repository
    init {
        repository = Repository(dataSource)
    }

    val database = dataSource
    //    val templates = database.templateDatabaseDao.getAllTemplates()
    val templates = repository.getAllTemplates()
    var templateId: Long = 0


    fun deleteTemplate(id: Int) {
        if (id == 0) {
            repository.deleteAllTemplates()
            repository.clearWeek()
            repository.clearDay()
            repository.clearExrcise()
            repository.clearIdStorage()

        } else {
            repository.deleteTemplate(id)
        }
    }
}

