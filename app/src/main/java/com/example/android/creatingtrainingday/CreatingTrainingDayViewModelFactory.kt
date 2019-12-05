package com.example.android.creatingtrainingday

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.database.TemplatesDatabase


class CreatingTrainingDayViewModelFactory (
    private val dataSource: TemplatesDatabase,
    private val application: Application
):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreatingTrainingDayViewModel::class.java)){
            return CreatingTrainingDayViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}