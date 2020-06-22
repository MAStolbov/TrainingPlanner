package com.example.android.creatingexercise

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.database.TemplatesDatabase

@Suppress("UNCHECKED_CAST")
class CreatingExerciseViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreatingExerciseViewModel() as T

    }
}