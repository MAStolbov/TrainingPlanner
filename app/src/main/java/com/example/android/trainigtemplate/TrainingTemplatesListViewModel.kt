package com.example.android.trainigtemplate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.database.TemplatesDatabase

class TrainingTemplatesListViewModel(dataSource: TemplatesDatabase, application: Application):
    AndroidViewModel(application) {

    val database = dataSource
    val templates = database.templateDatabaseDao.getAllTemplates()
    var templateId:Long = 0

    fun deleteTemplate(id:Int){
        if(id == 0){
            database.templateDatabaseDao.deleteAllTemplate()
            database.trainingWeekDao.clearWeek()
            database.trainingDayDao.clearDay()
            database.exerciseDao.clearExercise()
            database.idStorageDao.clearIdStorage()
        }else{
            database.templateDatabaseDao.deleteTemplate(id)
        }
    }
}

