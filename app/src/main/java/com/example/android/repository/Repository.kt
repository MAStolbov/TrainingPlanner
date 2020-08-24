package com.example.android.repository

import android.content.Context
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

const val EmptyPrimaryKey = 0L
const val NewId = 1L

class Repository(database: TemplatesDatabase) {

    private val templatesDao: TemplatesDatabaseDAO = database.templateDatabaseDao
    private val weeksDao: WeekDatabaseDAO = database.trainingWeekDao
    private val dayDao: DayDatabaseDAO = database.trainingDayDao
    private val exerciseDao: ExerciseDatabaseDAO = database.exerciseDao
    private val ioScope = CoroutineScope(Dispatchers.IO)


    //TemplateDatabaseDAO functions
    fun insertTemplate(template: TrainingTemplate) {
        templatesDao.insertTemplate(template)
    }

    fun updateTemplate(template: TrainingTemplate) {
        templatesDao.updateTemplate(template)
    }

    fun deleteTemplate(id: Long) {
        templatesDao.deleteTemplate(id)
    }

    fun deleteAllTemplates() {
        templatesDao.deleteAllTemplate()
    }

    suspend fun getTemplate(key: Long): TrainingTemplate {
        val template = ioScope.async {
            templatesDao.getTemplate(key)
        }
        return template.await()
    }

    fun returnMaxTemplateId(): Long? {
        return templatesDao.getTemplateMaxId()
    }


    fun getAllTemplates(): LiveData<List<TrainingTemplate>> {
        return templatesDao.getAllTemplates()
    }

    //WeekDatabaseDAO functions

    fun insertWeek(week: TrainingWeek) {
        weeksDao.insertWeek(week)
    }


    fun returnMaxWeekId(): Long? {
        return weeksDao.getWeekMaxId()
    }

    fun clearWeek() {
        weeksDao.clearWeek()
    }

    fun deleteWeeks(keys: MutableList<Long>) {
        ioScope.launch {
            weeksDao.deleteWeeks(keys)
        }
    }

    fun deleteWeeksForSpecificTemplate(key: Long) {
        weeksDao.deleteWeekForSpecificTemplate(key)
    }

    fun getWeeksForCurrentTemplate(key: Long): MutableList<TrainingWeek> {
        return weeksDao.getWeekForCurrentTemplate(key)
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

    fun deleteAllDays(){
        dayDao.deleteAllDays()
    }

    fun deleteDay(dayId: Long) {
        ioScope.launch {
            dayDao.deleteDay(dayId)
        }
    }

    fun deleteDays(weeksIdList: MutableList<Long>) {
        ioScope.launch {
            dayDao.deleteDays(weeksIdList)
        }
    }

    fun deleteDaysForSpecificTemplate(key: Long) {
        dayDao.deleteDaysForSpecificTemplate(key)
    }

    fun getDay(key: Long): TrainingDay {
        return dayDao.getDay(key)
    }


    fun getAllDays(): LiveData<List<TrainingDay>> {
        return dayDao.getAllDays()
    }

    fun getTrainingDaysForSpecificWeek(key: Long): MutableList<TrainingDay> {
        return dayDao.getTrainingDaysForSpecificWeek(key)
    }

    fun getTrainingDaysForAllWeek(keys: MutableList<Long>): MutableList<TrainingDay> {
        return dayDao.getTrainingDaysForAllWeek(keys)
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

    fun deleteExercises(keys: MutableList<Long>) {
        ioScope.launch {
            exerciseDao.deleteExercises(keys)
        }
    }

    fun deleteExercisesForSpecificTemplate(key: Long) {
        exerciseDao.deleteExercisesForSpecificTemplate(key)
    }

    fun getExercise(key: Long): Exercise {
        return exerciseDao.getExercise(key)
    }


    fun getAllExercises(): LiveData<List<Exercise>> {
        return exerciseDao.getAllExercises()
    }

    fun getExercisesForSpecificDay(key: Long): MutableList<Exercise> {
        return exerciseDao.getExercisesForSpecificDay(key)
    }

    fun getExercisesForAllDays(keys: MutableList<Long>): MutableList<Exercise> {
        return exerciseDao.getExercisesForAllDays(keys)
    }


    fun deleteSpecificTemplate(key: Long) {
        ioScope.launch {
            deleteTemplate(key)
            deleteWeeksForSpecificTemplate(key)
            deleteDaysForSpecificTemplate(key)
            deleteExercisesForSpecificTemplate(key)
        }
    }

    //записывает данные в базу данных
    fun saveData(temporaryDataStorage: TemporaryDataStorageClass) {
        temporaryDataStorage.deleteDataFromBase(this)

        ioScope.launch {
            saveTrainingTemplate(temporaryDataStorage)
            saveTrainingWeeksDaysExercises(temporaryDataStorage)
        }
    }

    private fun saveTrainingTemplate(temporaryDataStorage: TemporaryDataStorageClass) {
        //присвоение нового ID и запись TrainingTemplate  в базу данных
        val templateEntity = temporaryDataStorage.returnTemplateEntity()

        if (templateEntity.templateId == EmptyPrimaryKey) {
            templateEntity.templateId = getNewTemplateId()
            insertTemplate(templateEntity)
        } else {
            updateTemplate(temporaryDataStorage.templateEntity)
        }
    }

    private fun saveTrainingWeeksDaysExercises(temporaryDataStorage: TemporaryDataStorageClass) {
        val weeksDaysExercisesMap = temporaryDataStorage.weeksDaysExercisesMap
        val parentTemplateId = temporaryDataStorage.templateEntity.templateId

        //запись тренировочных Недель, Дней и Упражнений в базу данных
        //перед записью в базу происходи присвоение нового ID
        weeksDaysExercisesMap.forEach { (trainingWeek, mapOfDaysAndExerciseList) ->
            val weekId: Long
            if (trainingWeek.weekId == EmptyPrimaryKey) {
                weekId = getNewWeekId()
                trainingWeek.weekId = weekId
                trainingWeek.parentTemplateId = parentTemplateId
                insertWeek(trainingWeek)
            } else {
                weekId = trainingWeek.weekId
            }
            mapOfDaysAndExerciseList.forEach { (trainingDay, exerciseList) ->
                val newDayId: Long
                if (trainingDay.dayId == EmptyPrimaryKey) {
                    newDayId = getNewDayId()
                    trainingDay.dayId = newDayId
                    trainingDay.parentWeekId = weekId
                    trainingDay.parentTemplateId = parentTemplateId
                    insertDay(trainingDay)
                } else {
                    newDayId = trainingDay.dayId
                }
                exerciseList.forEach { exercise ->
                    val newExerciseId: Long
                    if (exercise.exerciseId == EmptyPrimaryKey) {
                        newExerciseId = getNewExerciseId()
                        exercise.exerciseId = newExerciseId
                        exercise.parentTrainingDayId = newDayId
                        exercise.parentTemplateId = parentTemplateId
                        insertExercise(exercise)
                    } else {
                        updateExercise(exercise)
                    }
                }
            }
        }
        temporaryDataStorage.clearAllData()
    }

    // генерирует новый ID на основание последнего ID из базы данных
    private fun getNewTemplateId(): Long {
        return returnMaxTemplateId()?.plus(1) ?: NewId
    }

    // генерирует новый ID на основание последнего ID из базы данных
    private fun getNewWeekId(): Long {
        return returnMaxWeekId()?.plus(1) ?: NewId
    }

    // генерирует новый ID на основание последнего ID из базы данных
    private fun getNewDayId(): Long {

        return returnMaxDayId()?.plus(1) ?: NewId
    }

    // генерирует новый ID на основание последнего ID из базы данных
    private fun getNewExerciseId(): Long {
        return returnMaxExerciseId()?.plus(1) ?: NewId
    }


    companion object {
        @Volatile
        private var repositoryInstance: Repository? = null

        fun getRepositoryInstance(context: Context) =
            repositoryInstance ?: Repository(TemplatesDatabase.getInstance(context)).also {
                repositoryInstance = it
            }
    }

}