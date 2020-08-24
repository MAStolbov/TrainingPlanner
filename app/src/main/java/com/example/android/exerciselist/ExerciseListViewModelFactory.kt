package com.example.android.exerciselist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.database.TemplatesDatabase
import com.example.android.repository.Repository


@Suppress("UNCHECKED_CAST")
class ExerciseListViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseListViewModel::class.java)) {
            return ExerciseListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}