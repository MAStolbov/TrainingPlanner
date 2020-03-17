package com.example.android.trainingdayslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.repository.Repository
import com.example.android.util.*


class TrainingDaysListViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {

    private val temporaryDataStorage = TemporaryDataStorageClass.instance
    private val repository: Repository

    init {
        repository = Repository(dataSource)
    }


    var numberOfWeeks: Int = 0
    lateinit var firstTrainingWeek: MutableMap<Int, Boolean>
    lateinit var secondTrainingWeek: MutableMap<Int, Boolean>
    lateinit var thirdTrainingWeek: MutableMap<Int, Boolean>
    lateinit var fourthTrainingWeek: MutableMap<Int, Boolean>

    var templateName: String = ""
    var templateDescription = ""


    private val _fillInDay = MutableLiveData<Boolean>()
    val fillInDay: LiveData<Boolean>
        get() = _fillInDay

    private val _showSelectedTrainingDys = MutableLiveData<Boolean>()
    val showSelectedTrainingDays: LiveData<Boolean>
        get() = _showSelectedTrainingDys

    private val _showWeeks = MutableLiveData<Boolean>()
    val showWeeks: LiveData<Boolean>
        get() = _showWeeks

    fun getNumberOfWeeks() {
        numberOfWeeks = SelectedTrainingDaysAndWeeks.returnNumberOfWeeks()
        _showWeeks.value = true
    }

    fun getSelectedTrainingDays() {
        firstTrainingWeek = SelectedTrainingDaysAndWeeks.returnFirstTrainingWeek()
        secondTrainingWeek = SelectedTrainingDaysAndWeeks.returnSecondTrainingWeek()
        thirdTrainingWeek = SelectedTrainingDaysAndWeeks.returnThirdTrainingWeek()
        fourthTrainingWeek = SelectedTrainingDaysAndWeeks.returnFourthTrainingWeek()

        _showSelectedTrainingDys.value = true
    }

    fun getTemplateNameAndDescription() {
        val trainingTemplate = temporaryDataStorage.returnTemplateEntity()
        templateName = "Template name:${trainingTemplate.templateName}"
        templateDescription = "Template description: ${trainingTemplate.templateDescription}"
    }

    fun fillInDay(weekNumber: Int, day: Int, buttonNumber: Int) {
        TrainingWeekData.sendDayAndNumberOfTheWeek(weekNumber, day)
        _fillInDay.value = true
    }


    fun prepareAndSaveData(entityStorage: TemporaryDataStorage) {
        entityStorage.putAtWeeksDaysExerciseMap()
        repository.putDataInDatabase(entityStorage)

    }

}