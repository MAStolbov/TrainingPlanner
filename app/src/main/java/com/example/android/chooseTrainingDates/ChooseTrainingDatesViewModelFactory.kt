package com.example.android.chooseTrainingDates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.repository.Repository


@Suppress("UNCHECKED_CAST")
class ChooseTrainingDatesViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChooseTrainingDatesViewModel(repository) as T
    }
}