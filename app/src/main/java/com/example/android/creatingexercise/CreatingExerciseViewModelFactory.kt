package com.example.android.creatingexercise

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.database.TemplatesDatabase
import com.example.android.repository.Repository

@Suppress("UNCHECKED_CAST")
class CreatingExerciseViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreatingExerciseViewModel(repository) as T

    }
}