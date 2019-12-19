package com.example.android.creatingexercise

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.database.TemplatesDatabase

class CreatingExerciseViewModelFactory (
    private val dataSource: TemplatesDatabase,
    private val application: Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(CreatingExerciseViewModel::class.java)){
           return CreatingExerciseViewModel(dataSource,application) as T
       }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}