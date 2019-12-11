package com.example.android.trainingdayslist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.database.TemplatesDatabase


class TrainingDaysListViewModelFactory (
    private val dataSource: TemplatesDatabase,
    private val application: Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrainingDaysListViewModel::class.java)){
            return TrainingDaysListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}