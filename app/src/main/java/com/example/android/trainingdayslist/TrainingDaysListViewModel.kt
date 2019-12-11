package com.example.android.trainingdayslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.util.SelectedTrainingDays
import com.example.android.util.TemplateNameAndDescription
import com.example.android.database.TemplatesDatabase


class TrainingDaysListViewModel (dataSource: TemplatesDatabase, application: Application) :
    ViewModel(){
    val database = dataSource

    lateinit var firstTrainingWeek:MutableMap<Int, Boolean>

    var templateName:String = ""


    private val _showSelectedTrainingDys = MutableLiveData<Boolean>()
    val showSelectedTrainingDays:LiveData<Boolean>
        get() = _showSelectedTrainingDys

    fun getSelectedTrainingDays(){
        firstTrainingWeek = SelectedTrainingDays.returnFirstTrainingWeek()
        _showSelectedTrainingDys.value = true
    }

    fun getTemplateName(){
        templateName ="Template name:${TemplateNameAndDescription.templateName}"
    }
}