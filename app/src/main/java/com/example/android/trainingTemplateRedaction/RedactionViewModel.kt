package com.example.android.trainingTemplateRedaction

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingweekEntityDao.TrainingWeek
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass
import kotlinx.coroutines.*

class RedactionViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val temporaryDataStorage = TemporaryDataStorageClass.instance
    private val repository: Repository = Repository(dataSource)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    var template = TrainingTemplate()
    var templateId: Long = 0
    var weeksList = mutableListOf<TrainingWeek>()

    var textForScreen = ""

    private val _endDataLoading = MutableLiveData<Boolean>()
    val endDataLoading: LiveData<Boolean>
        get() = _endDataLoading


    fun startDataLoading() {
        mainScope.launch {
            temporaryDataStorage.loadData(templateId, repository)
            template = temporaryDataStorage.returnTemplateEntity()
            weeksList = temporaryDataStorage.returnWeeksList()
            _endDataLoading.value = true
        }
    }

    fun returnWeek(): String {
        val week = temporaryDataStorage.returnWeek()
        val weekList = repository.returnWeeksList()

        return "List size:${weekList.value}"
    }

    fun setTextForScreen(): String {
        val textForTemplate =
            "Template ID:${template.templateId}, Template name:${template.templateName}"
        val textForWeeks = "Weeks Number:${weeksList.size}"
        textForScreen = "$textForTemplate $textForWeeks"
        return textForScreen
    }


}