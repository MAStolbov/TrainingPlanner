package com.example.android.exerciselist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.database.TemplatesDatabase


@Suppress("UNCHECKED_CAST")
class ExerciseListViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseListViewModel::class.java)) {
            return ExerciseListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}