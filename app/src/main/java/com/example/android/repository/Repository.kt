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

class Repository(private val database: TemplatesDatabase) {

    private val templatesDao: TemplatesDatabaseDAO = database.templateDatabaseDao
    private val weeksDao: WeekDatabaseDAO = database.trainingWeekDao
    private val dayDao: DayDatabaseDAO = database.trainingDayDao
    private val exerciseDao: ExerciseDatabaseDAO = database.exerciseDao
    private val temporaryDataStorage = TemporaryDataStorageClass.instance


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

    fun getTemplate(key: Long) {
        templatesDao.getTemplate(key)
    }

    fun returnMaxTemplateId(): Long? {
        return templatesDao.getTemplateMaxId()
    }


    fun getAllTemplates(): LiveData<List<TrainingTemplate>> {
        return templatesDao.getAllTemplates()
    }

    //WeekDatabaseDao functions
//    fun insertWeek(week:TrainingWeek) : Long{
//        var id = 0L
//        synchronized(this) {
//            id = getNewId()
//            week.weekId = id + 1
//            weeksDao.insertWeek(week)
//        }
//        return id
//    }

    fun insertWeek(week: TrainingWeek?) {
        weeksDao.insertWeek(week)
    }

    fun updateWeek(week: TrainingWeek) {
        weeksDao.updateWeek(week)
    }

    fun returnMaxWeekId(): Long? {
        return weeksDao.getWeekMaxId()
    }

    fun clearWeek() {
        weeksDao.clearWeek()
    }

    fun getWeek(key: Long) {
        weeksDao.getWeek(key)
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
    fun putDataInDatabase() {

        val newTemplateId = getNewTemplateId()

        //присвоение нового ID и запись TrainingTemplate  в базу данных
        val templateEntity = temporaryDataStorage.returnTemplateEntity()
        templateEntity.templateId = newTemplateId
        insertTemplate(templateEntity)

        //получение из EntityStorage коллекции с тренировочными Неделями,Днями и Упражнениями
        val weeksDaysExercisesMap = temporaryDataStorage.weeksDaysExercisesMap

        //запись тренировочных Недель, Дней и Упражнений в базу данных
        //перед записью в базу происходи присвоение нового ID
        for ((key, value) in weeksDaysExercisesMap) {
            val newWeekId = getNewWeekId()
                key.weekId = newWeekId
                key.parentTemplateId = newTemplateId
            insertWeek(key)
            for ((key, value) in value) {
                val newDayId = getNewDayId()
                key.dayId = newDayId
                key.parentWeekId = newWeekId
                insertDay(key)
                for (exercise in value) {
                    val newExerciseId = getNewExerciseId()
                    exercise.exerciseId = newExerciseId
                    exercise.parentTrainingDayId = newDayId
                    insertExercise(exercise)
                }
            }
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
    private fun getNewWeekId(): Long {
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