package com.example.android.trainingdayslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.util.SelectedTrainingDaysAndWeeks
import com.example.android.util.TemplateNameAndDescription
import com.example.android.database.TemplatesDatabase


class TrainingDaysListViewModel (dataSource: TemplatesDatabase, application: Application) :
    ViewModel(){
    val database = dataSource

    var numberOfWeeks:Int = 0
    lateinit var firstTrainingWeek:MutableMap<Int, Boolean>
    lateinit var secondTrainingWeek:MutableMap<Int, Boolean>
    lateinit var thirdTrainingWeek:MutableMap<Int, Boolean>
    lateinit var fourthTrainingWeek:MutableMap<Int, Boolean>

    var templateName:String = ""
    var templateDescription = ""


    private val _showSelectedTrainingDys = MutableLiveData<Boolean>()
    val showSelectedTrainingDays:LiveData<Boolean>
        get() = _showSelectedTrainingDys

    private  val _showWeeks = MutableLiveData<Boolean>()
    val showWeeks:LiveData<Boolean>
        get() = _showWeeks

    fun getNumberOfWeeks(){
        numberOfWeeks = SelectedTrainingDaysAndWeeks.returnNumberOfWeeks()
        _showWeeks.value = true
    }

    fun getSelectedTrainingDays(){
        firstTrainingWeek = SelectedTrainingDaysAndWeeks.returnFirstTrainingWeek()
        secondTrainingWeek = SelectedTrainingDaysAndWeeks.returnSecondtTrainingWeek()
        thirdTrainingWeek = SelectedTrainingDaysAndWeeks.returnThirdTrainingWeek()
        fourthTrainingWeek = SelectedTrainingDaysAndWeeks.returnFourthTrainingWeek()

        _showSelectedTrainingDys.value = true
    }

    fun getTemplateNameAndDescription(){
        templateName ="Template name:${TemplateNameAndDescription.templateName}"
        templateDescription = "Template description: ${TemplateNameAndDescription.templateDescription}"
    }
}