package com.example.android.trainigtemplatelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrainingTemplatesListViewModel(dataSource: TemplatesDatabase, application: Application) :
    AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val repository:Repository
    init {
        repository = Repository(dataSource)
    }

    val database = dataSource
    val templates = repository.getAllTemplates()
    var templateId: Long = 0


    fun deleteTemplate(id: Int) {
        ioScope.launch {
            if (id == 0) {
                repository.deleteAllTemplates()
                repository.clearWeek()
                repository.clearDay()
                repository.clearExrcise()

            } else {
                repository.deleteTemplate(id)
            }
        }
    }
}

