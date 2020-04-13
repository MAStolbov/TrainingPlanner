package com.example.android.trainingTemplateRedaction

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingweekEntityDao.TrainingWeek
import com.example.android.repository.Repository
import com.example.android.util.Stub
import com.example.android.util.TemporaryDataStorageClass
import kotlinx.coroutines.*

class RedactionViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val temporaryDataStorage = TemporaryDataStorageClass.instance
    private val repository: Repository = Repository(dataSource)
    private val defaultScope = CoroutineScope(Dispatchers.Default)

    var template = TrainingTemplate()
    var templateId: Long = 0
    var weeksList = listOf<TrainingWeek>()

    var textForScreen = ""

    private val _endDataLoading = MutableLiveData<Boolean>()
    val endDataLoading: LiveData<Boolean>
        get() = _endDataLoading


    fun startDataLoading() {
        defaultScope.launch {
            template = temporaryDataStorage.getTrainingTemplate(templateId,repository)
            weeksList = temporaryDataStorage.getTrainingWeeks(templateId,repository)
            textForScreen = Stub.textForTemplateInfo
            delay(1000)
            withContext(Dispatchers.Main){
                _endDataLoading.value = true
            }
        }
    }


    fun setTextForScreen(): String {
        val textForTemplate = "Template ID:${template.templateId}, Template name:${template.templateName}"
        val textForWeeks = "Weeks Number:${weeksList.size}"
        textForScreen = "$textForWeeks $textForTemplate"
        return textForScreen
    }


}