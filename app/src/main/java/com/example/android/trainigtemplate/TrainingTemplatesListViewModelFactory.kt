package com.example.android.trainigtemplate

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.database.TemplatesDatabase
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO

class TrainingTemplatesListViewModelFactory(
    private val dataSource: TemplatesDatabase,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrainingTemplatesListViewModel::class.java)) {
            return TrainingTemplatesListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}