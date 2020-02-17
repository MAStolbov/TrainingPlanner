package com.example.android.repository

import androidx.lifecycle.LiveData
import com.example.android.database.TemplatesDatabase
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.exerciseEntityDao.ExerciseDatabaseDAO
import com.example.android.database.idStorageEntityDao.IdStorageDatabaseDAO
import com.example.android.database.idStorageEntityDao.IdStorageEntity
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingdayEntityDAO.DayDatabaseDAO
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.database.trainingweekEntityDao.TrainingWeek
import com.example.android.database.trainingweekEntityDao.WeekDatabaseDAO

class Repository(private val database:TemplatesDatabase) {

    private val templatesDao:TemplatesDatabaseDAO = database.templateDatabaseDao
    private val weeksDao: WeekDatabaseDAO = database.trainingWeekDao
    private val dayDao: DayDatabaseDAO = database.trainingDayDao
    private val exerciseDao: ExerciseDatabaseDAO = database.exerciseDao
    private val idStorageDao: IdStorageDatabaseDAO = database.idStorageDao

    //TemplateDatabaseDAO functions
    fun insertTemplate(template: TrainingTemplate){
        templatesDao.insertTemplate(template)
    }

    fun updateTemplate(template: TrainingTemplate){
        templatesDao.updateTemplate(template)
    }

    fun deleteTemplate(id:Int){
        templatesDao.deleteTemplate(id)
    }

    fun deleteAllTemplates(){
        templatesDao.deleteAllTemplate()
    }

    fun getTemplate(key:Long){
        templatesDao.getTemplate(key)
    }


    fun getAllTemplates(): LiveData<List<TrainingTemplate>>{
        return templatesDao.getAllTemplates()
    }

    //WeekDatabaseDao functions
    fun insertWeek(week:TrainingWeek){
        weeksDao.insertWeek(week)
    }

    fun updateWeek(week:TrainingWeek) {
        weeksDao.updateWeek(week)
    }

    fun clearWeek(){
        weeksDao.clearWeek()
    }

    fun getWeek(key:Long){
        weeksDao.getWeek(key)
    }


    fun getAllWeeks():LiveData<List<TrainingWeek>>{
        return weeksDao.getAllWeeks()
    }

    //DayDatabaseDao functions

    fun insertDay(day: TrainingDay){
        dayDao.insertDay(day)
    }

    fun updateDay(day: TrainingDay){
        dayDao.updateDay(day)
    }

    fun clearDay(){
        dayDao.clearDay()
    }

    fun getDay(key:Long):TrainingDay{
        return dayDao.getDay(key)
    }


    fun getAllDays(): LiveData<List<TrainingDay>>{
        return dayDao.getAllDays()
    }

    //ExerciseDatabaseDao functions

    fun insertExercise(exercise: Exercise){
        exerciseDao.insertExercise(exercise)
    }

    fun updateExercise(exercise: Exercise){
        exerciseDao.updateExercise(exercise)
    }

    fun clearExrcise(){
        exerciseDao.clearExercise()
    }

    fun getExercise(key:Long): Exercise{
        return exerciseDao.getExercise(key)
    }


    fun getAllExercises(): LiveData<List<Exercise>>{
        return exerciseDao.getAllExercises()
    }

    //IdStorageDatabaseDao functions

    fun insertIdStorage(idStorage: IdStorageEntity){
        idStorageDao.insertIdStorage(idStorage)
    }

    fun updateIdStorage(idStorage: IdStorageEntity){
        idStorageDao.updateIdStorage(idStorage)
    }

    fun returnMaxTemplateId():Long?{
        return idStorageDao.returnMaxTemplateId()
    }

    fun returnMaxWeekId():Long?{
        return idStorageDao.returnMaxWeekId()
    }

    fun returnMaxDayId():Long?{
        return idStorageDao.returnMaxDayId()
    }

    fun returnMaxExerciseId():Long?{
        return idStorageDao.returnMaxExerciseId()
    }

    fun clearIdStorage(){
        idStorageDao.clearIdStorage()
    }

}