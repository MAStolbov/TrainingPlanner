package com.example.android.trainingTemplateRedaction

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass
import kotlinx.coroutines.*

class RedactionViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val temporaryDataStorage = TemporaryDataStorageClass.instance
    private val repository: Repository = Repository(dataSource)
    private val defaultScope = CoroutineScope(Dispatchers.Main)


    var templateId: Long = 0
    var templateName: String = ""
    var templateDescription: String = ""
    var imageNumber = MutableLiveData<Int>()

    fun changeButtonVisibility(number: Int) {
        imageNumber.value = number
    }

    fun showingWeek(weekNumber: Int): Boolean {
        return temporaryDataStorage.returnWeekListSize() >= weekNumber
    }

    fun addNewWeek(weekNumber:Int) {
        temporaryDataStorage.createTrainingWeek(weekNumber)
    }

    fun deleteWeek(weekNumber: Int) {

    }

    fun startDataLoading() {
        defaultScope.launch {
            //имтирует долгое скачивание данных
            delay(1000)
            temporaryDataStorage.startDataDownloading(templateId, repository)
        }
    }

    fun setTextForScreen() {
        templateName = temporaryDataStorage.templateEntity.templateName
        templateDescription = temporaryDataStorage.templateEntity.templateDescription
    }

    fun setNewTrainingTemplateName(name: String) {
        templateName = name
        temporaryDataStorage.setNewTrainingTemplateName(name)
    }

    fun setNewTrainingTemplateDescription(description: String) {
        templateDescription = description
        temporaryDataStorage.setNewTrainingTemplateDescription(description)
    }

    fun checkExistDays(weekNumber: Int, dayNumber: Int): Boolean {
        return temporaryDataStorage.checkExistDays(weekNumber, dayNumber)
    }

    fun clearData() {
        temporaryDataStorage.clearAllData()
    }

    fun updateData() {
        repository.updateData(temporaryDataStorage)
    }

}