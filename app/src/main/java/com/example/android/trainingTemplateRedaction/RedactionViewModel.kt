package com.example.android.trainingTemplateRedaction

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RedactionViewModel (dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val temporaryDataStorage = TemporaryDataStorageClass.instance
    private val repository: Repository = Repository(dataSource)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    var template = TrainingTemplate()
    var templateId:Long = 0

    private val _endDataLoading = MutableLiveData<Boolean>()
    val endDataLoading: LiveData<Boolean>
        get() = _endDataLoading

    fun startDataLoading(){
        mainScope.launch {
            temporaryDataStorage.loadData(repository.getTemplate(templateId))
            template = temporaryDataStorage.returnTemplateEntity()
            _endDataLoading.value = true
        }
    }


}