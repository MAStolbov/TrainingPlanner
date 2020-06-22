package com.example.android.trainigtemplatelist


import androidx.lifecycle.ViewModel
import com.example.android.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrainingTemplatesListViewModel(private val repository: Repository) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    val templates = repository.getAllTemplates()
    var templateId: Long = 0

    fun deleteTemplate(id: Long) {
        ioScope.launch {
            if (id == 0L) {
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

