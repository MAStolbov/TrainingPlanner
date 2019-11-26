package com.example.android.trainigtemplate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import com.example.android.database.templateEntityDao.TrainingTemplate

class TrainingTemplatesListViewModel(dataSource: TemplatesDatabaseDAO, application: Application):
    AndroidViewModel(application) {

    val database = dataSource

}

