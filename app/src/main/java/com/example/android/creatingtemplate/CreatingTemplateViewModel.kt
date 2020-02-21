package com.example.android.creatingtemplate

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.util.SelectedTrainingDaysAndWeeks
import com.example.android.util.TemplateNameAndDescription
import com.example.android.database.TemplatesDatabase
import com.example.android.database.idStorageEntityDao.IdStorageEntity
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingweekEntityDao.TrainingWeek
import com.example.android.repository.Repository
import com.example.android.util.EntityStorage
import com.example.android.util.TrainingWeekData


class CreatingTemplateViewModel(dataSource: TemplatesDatabase, application: Application) :
    ViewModel() {


    val database = dataSource

    private val repository: Repository

    init {
        repository = Repository(dataSource)
    }


    private var clicksCount: Int = 0


    //private var weeks: MutableMap<Int, MutableMap<Int, Boolean>>

    private var week1 = initMap()
    private var week2 = initMap()
    private var week3 = initMap()
    private var week4 = initMap()

    private fun initMap(): MutableMap<Int, Boolean> {
        return mutableMapOf(
            1 to false, 2 to false, 3 to false,
            4 to false, 5 to false, 6 to false, 7 to false
        )
    }

    private val _addNewWeek = MutableLiveData<Int>()
    val addNewWeek: LiveData<Int>
        get() = _addNewWeek

    private val _maxWeek = MutableLiveData<Boolean>()
    val maxWeek: LiveData<Boolean>
        get() = _maxWeek


    fun createTemplate(name: String, description: String) {
        val newTemplate = TrainingTemplate()
        newTemplate.templateName = name
        newTemplate.templateDescription = description
        newTemplate.numberOfTrainingWeeks = clicksCount
        saveTemplateEntity(newTemplate)
        saveTemplateNameAndDescription(name, description)
    }


    private fun saveTemplateEntity(entity: TrainingTemplate) {
        EntityStorage.addNewTemplateEntity(entity)
    }

    private fun saveTemplateNameAndDescription(name: String, description: String) {
        TemplateNameAndDescription.templateName = name
        TemplateNameAndDescription.templateDescription = description
    }

    fun addWeek() {
        if (clicksCount < 4) {
            clicksCount += 1
            val newWeek = TrainingWeek()
            newWeek.weekNumber = clicksCount
            when(clicksCount){
                1 -> EntityStorage.trainingWeek1 = newWeek
                2 -> EntityStorage.trainingWeek2 = newWeek
                3 -> EntityStorage.trainingWeek3 = newWeek
                4 -> EntityStorage.trainingWeek4 = newWeek
            }
            _addNewWeek.value = clicksCount
        } else {
            _maxWeek.value = true
        }
    }


    fun selectFirstWeekTrainingDay(dayNumber: Int) {
        week1[dayNumber] = week1[dayNumber] != true
    }

    fun selectSecondWeekTrainingDay(dayNumber: Int) {
        week2[dayNumber] = week2[dayNumber] != true
    }

    fun selectThirdWeekTrainingDay(dayNumber: Int) {
        week3[dayNumber] = week3[dayNumber] != true
    }

    fun selectFourthWeekTrainingDay(dayNumber: Int) {
        week4[dayNumber] = week4[dayNumber] != true
    }


    fun sendSelectedTrainingDays() {
        SelectedTrainingDaysAndWeeks.sendSelectedDays(week1, week2, week3, week4)
    }

    fun sendNumberOfWeeks() {
        SelectedTrainingDaysAndWeeks.sendNumberOfWeeks(clicksCount)
    }

}