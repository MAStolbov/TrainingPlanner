package com.example.android.repository

import androidx.lifecycle.LiveData
import com.example.android.database.TemplatesDatabase
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.exerciseEntityDao.ExerciseDatabaseDAO
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingdayEntityDAO.DayDatabaseDAO
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.database.trainingweekEntityDao.TrainingWeek
import com.example.android.database.trainingweekEntityDao.WeekDatabaseDAO
import com.example.android.util.TemporaryDataStorageClass
import kotlinx.coroutines.*

class Repository(database: TemplatesDatabase) {

    private val templatesDao: TemplatesDatabaseDAO = database.templateDatabaseDao
    private val weeksDao: WeekDatabaseDAO = database.trainingWeekDao
    private val dayDao: DayDatabaseDAO = database.trainingDayDao
    private val exerciseDao: ExerciseDatabaseDAO = database.exerciseDao
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val temporaryDataStorage = TemporaryDataStorageClass.instance

    //private var temporaryDataStorage: TemporaryDataStorageClass? = null //TemporaryDataStorageClass.instance


    //TemplateDatabaseDAO functions
    fun insertTemplate(template: TrainingTemplate) {
        templatesDao.insertTemplate(template)
    }

    fun updateTemplate(template: TrainingTemplate) {
        templatesDao.updateTemplate(template)
    }

    fun deleteTemplate(id: Int) {
        templatesDao.deleteTemplate(id)
    }

    fun deleteAllTemplates() {
        templatesDao.deleteAllTemplate()
    }

   fun getTemplate(key: Long):TrainingTemplate{
        return templatesDao.getTemplate(key)
    }

    fun returnMaxTemplateId(): Long? {
        return templatesDao.getTemplateMaxId()
    }


    fun getAllTemplates(): LiveData<List<TrainingTemplate>> {
        return templatesDao.getAllTemplates()
    }

    fun insertWeek(week: TrainingWeek) {
        weeksDao.insertWeek(week)
    }

    fun updateWeek(week: TrainingWeek) {
        weeksDao.updateWeek(week)
    }

    suspend fun returnMaxWeekId(): Long? {
        val weekMaxId = ioScope.async { weeksDao.getWeekMaxId() }
        return weekMaxId.await()
    }

    fun clearWeek() {
        weeksDao.clearWeek()
    }

    fun getWeek(key: Long) {
        weeksDao.getWeek(key)
    }


    suspend fun getWeeksForCurrentTemplate(key: Long): TrainingWeek {
        val week = ioScope.async {
            weeksDao.getWeekForCurrentTemplate(key)
        }
        return week.await()
    }

    fun returnWeeksList(key: Long):List<TrainingWeek>{
        return weeksDao.returnWeeksList(key)
    }


    fun getAllWeeks(): LiveData<List<TrainingWeek>> {
        return weeksDao.getAllWeeks()
    }

    //DayDatabaseDao functions

    fun insertDay(day: TrainingDay) {
        dayDao.insertDay(day)
    }

    fun updateDay(day: TrainingDay) {
        dayDao.updateDay(day)
    }

    fun returnMaxDayId(): Long? {
        return dayDao.getDayMaxId()
    }

    fun clearDay() {
        dayDao.clearDay()
    }

    fun getDay(key: Long): TrainingDay {
        return dayDao.getDay(key)
    }


    fun getAllDays(): LiveData<List<TrainingDay>> {
        return dayDao.getAllDays()
    }

    //ExerciseDatabaseDao functions

    fun insertExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }

    fun updateExercise(exercise: Exercise) {
        exerciseDao.updateExercise(exercise)
    }

    fun returnMaxExerciseId(): Long? {
        return exerciseDao.getExerciseMaxId()
    }

    fun clearExrcise() {
        exerciseDao.clearExercise()
    }

    fun getExercise(key: Long): Exercise {
        return exerciseDao.getExercise(key)
    }


    fun getAllExercises(): LiveData<List<Exercise>> {
        return exerciseDao.getAllExercises()
    }


    //записывает данные в базу данных
    fun saveData(temporaryDataStorage: TemporaryDataStorageClass) {
        ioScope.launch {
            val newTemplateId = getNewTemplateId()

            //присвоение нового ID и запись TrainingTemplate  в базу данных
            val templateEntity = temporaryDataStorage.returnTemplateEntity()
            templateEntity.templateId = newTemplateId
            insertTemplate(templateEntity)


            val week1 = temporaryDataStorage.week1
            if (week1 !=null) {
                week1.parentTemplateId = newTemplateId
                week1.weekId = getNewWeekId()
                week1.weekNumber = 1
                weeksDao.insertWeek(week1)
            }
            val week2 = temporaryDataStorage.week2
            if (week2 !=null) {
                week2.parentTemplateId = newTemplateId
                week2.weekId = getNewWeekId()
                week2.weekNumber = 2
                weeksDao.insertWeek(week2)
            }
            val week3 = temporaryDataStorage.week3
            if (week3 !=null) {
                week3.parentTemplateId = newTemplateId
                week3.weekId = getNewWeekId()
                week3.weekNumber = 3
                weeksDao.insertWeek(week3)
            }
            val week4 = temporaryDataStorage.week4
            if (week4 !=null) {
                week4.parentTemplateId = newTemplateId
                week4.weekId = getNewWeekId()
                week4.weekNumber = 4
                weeksDao.insertWeek(week4)
            }


            //получение из EntityStorage коллекции с тренировочными Неделями,Днями и Упражнениями
//            val weeksDaysExercisesMap = temporaryDataStorage.weeksDaysExercisesMap
//
//            //запись тренировочных Недель, Дней и Упражнений в базу данных
//            //перед записью в базу происходи присвоение нового ID
//            for ((key, value) in weeksDaysExercisesMap) {
//                val newWeekId = getNewWeekId()
//                key.weekId = newWeekId
//                key.parentTemplateId = newTemplateId
//                insertWeek(key)
//                for ((key, value) in value) {
//                    val newDayId = getNewDayId()
//                    key.dayId = newDayId
//                    key.parentWeekId = newWeekId
//                    insertDay(key)
//                    for (exercise in value) {
//                        val newExerciseId = getNewExerciseId()
//                        exercise.exerciseId = newExerciseId
//                        exercise.parentTrainingDayId = newDayId
//                        insertExercise(exercise)
//                    }
//                }
//            }
        }
    }

    // генерирует новый ID на основание последнего ID из базы данных
    private fun getNewTemplateId(): Long {
        var newTemplateId = returnMaxTemplateId()
        if (newTemplateId == null) {
            newTemplateId = 1
        } else {
            newTemplateId += 1
        }
        return newTemplateId
    }

    // генерирует новый ID на основание последнего ID из базы данных
    private suspend fun getNewWeekId(): Long {
        var newWeekId = returnMaxWeekId()
        if (newWeekId == null) {
            newWeekId = 1
        } else {
            newWeekId += 1
        }
        return newWeekId
    }

    // генерирует новый ID на основание последнего ID из базы данных
    private fun getNewDayId(): Long {
        var newDayId = returnMaxDayId()
        if (newDayId == null) {
            newDayId = 1
        } else {
            newDayId += 1
        }
        return newDayId
    }

    // генерирует новый ID на основание последнего ID из базы данных
    private fun getNewExerciseId(): Long {
        var newExerciseId = returnMaxExerciseId()
        if (newExerciseId == null) {
            newExerciseId = 1
        } else {
            newExerciseId += 1
        }
        return newExerciseId
    }

}