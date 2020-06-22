package com.example.android.trainingTemplateRedaction

import androidx.lifecycle.ViewModel
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass
import kotlinx.coroutines.*

class RedactionViewModel(private val repository: Repository) : ViewModel() {

    private val temporaryDataStorage = TemporaryDataStorageClass.instance
    private val defaultScope = CoroutineScope(Dispatchers.Main)


    var templateId: Long = 0
    var templateName: String = ""
    var templateDescription: String = ""

    fun showingWeek(weekNumber: Int): Boolean {
        return temporaryDataStorage.checkWeekExist(weekNumber)
    }

    fun createTrainingTemplate() {
        temporaryDataStorage.createTrainingTemplate()
    }

    fun addNewWeek(weekNumber: Int) {
        temporaryDataStorage.createTrainingWeek(weekNumber)
    }

    fun deleteWeek(weekNumber: Int) {
        temporaryDataStorage.deleteWeek(weekNumber)
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

    fun createTrainingDay(weekNumber: Int, dayNumber: Int) {
        temporaryDataStorage.createTrainingDay(weekNumber, dayNumber)
    }

    fun checkExistDays(weekNumber: Int, dayNumber: Int): Boolean {
        return temporaryDataStorage.checkExistDays(weekNumber, dayNumber)
    }

    fun saveData() {
        temporaryDataStorage.packDataAtMap()
        repository.saveData(temporaryDataStorage)
    }

    fun deleteTemplate() {

    }
}