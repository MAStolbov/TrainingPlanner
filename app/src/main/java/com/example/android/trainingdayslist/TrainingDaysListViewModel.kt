package com.example.android.trainingdayslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase
import com.example.android.repository.Repository
import com.example.android.util.*


class TrainingDaysListViewModel (dataSource: TemplatesDatabase, application: Application) :
    ViewModel(){

    private val repository: Repository
    init {
        repository = Repository(dataSource)
    }


    var numberOfWeeks:Int = 0
    lateinit var firstTrainingWeek:MutableMap<Int, Boolean>
    lateinit var secondTrainingWeek:MutableMap<Int, Boolean>
    lateinit var thirdTrainingWeek:MutableMap<Int, Boolean>
    lateinit var fourthTrainingWeek:MutableMap<Int, Boolean>

    var templateName:String = ""
    var templateDescription = ""


    private val _fillInDay = MutableLiveData<Boolean>()
    val fillInDay:LiveData<Boolean>
        get() = _fillInDay

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
        secondTrainingWeek = SelectedTrainingDaysAndWeeks.returnSecondTrainingWeek()
        thirdTrainingWeek = SelectedTrainingDaysAndWeeks.returnThirdTrainingWeek()
        fourthTrainingWeek = SelectedTrainingDaysAndWeeks.returnFourthTrainingWeek()

        _showSelectedTrainingDys.value = true
    }

    fun getTemplateNameAndDescription(){
        templateName ="Template name:${TemplateNameAndDescription.templateName}"
        templateDescription = "Template description: ${TemplateNameAndDescription.templateDescription}"
    }

    fun fillInDay(weekNumber:Int, day:Int, buttonNumber:Int){
        TrainingWeekData.sendDayAndNumberOfTheWeek(weekNumber,day)
        _fillInDay.value = true
    }

    fun putEntitysInDatabase(){
        EntityStorage.putToWeeksDaysExerciseMap()
        val newTemplateId = getNewTemplateId()

        val templateEntity = EntityStorage.returnTemplateEntity()
        templateEntity.templateId =newTemplateId
        repository.insertTemplate(templateEntity)


        val weeksDaysExercisesMap = EntityStorage.weeksDaysExercisesMap
        for ((key,value)in weeksDaysExercisesMap){
            val newWeekId = getNewWeekId()
            key.weekId = newWeekId
            key.parentTemplateId = newTemplateId
            repository.insertWeek(key)
            for((key,value)in value){
                val newDayId = getNewDayId()
                key.dayId = newDayId
                key.parentWeekId = newWeekId
                repository.insertDay(key)
                for (exercise in value){
                    val newExerciseId = getNewExerciseId()
                    exercise.exerciseId = newExerciseId
                    exercise.parentTrainingDayId = newDayId
                    repository.insertExercise(exercise)
                }
            }
        }
    }

    private fun getNewTemplateId():Long {
        var newTemplateId = repository.returnMaxTemplateId()
        if (newTemplateId == null) {
            newTemplateId = 1
        } else {
            newTemplateId += 1
        }
        return newTemplateId
    }

    private fun getNewWeekId():Long {
        var newWeekId = repository.returnMaxWeekId()
        if (newWeekId == null) {
            newWeekId = 1
        } else {
            newWeekId += 1
        }
        return newWeekId
    }

    private fun getNewDayId():Long {
        var newDayId = repository.returnMaxDayId()
        if (newDayId == null) {
            newDayId = 1
        } else {
            newDayId += 1
        }
        return newDayId
    }

    private fun getNewExerciseId():Long {
        var newExerciseId = repository.returnMaxExerciseId()
        if (newExerciseId == null) {
            newExerciseId = 1
        } else {
            newExerciseId += 1
        }
        return newExerciseId
    }




}