package com.example.android.trainingplanner

import android.app.Application
import com.example.android.chooseTrainingDates.ChooseTrainingDatesViewModel
import com.example.android.creatingexercise.CreatingExerciseFragment
import com.example.android.creatingexercise.CreatingExerciseViewModel
import com.example.android.exerciselist.ExerciseListViewModel
import com.example.android.repository.Repository
import com.example.android.trainigtemplatelist.TrainingTemplatesListViewModel
import com.example.android.trainingTemplateRedaction.RedactionViewModel
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Koin : Application() {

    private val koinModule = module {
        single { Repository.getRepositoryInstance(baseContext) }
        viewModel { TrainingTemplatesListViewModel(get()) }
        viewModel { ChooseTrainingDatesViewModel(get()) }
        viewModel { CreatingExerciseViewModel(get()) }
        viewModel { ExerciseListViewModel(get()) }
        viewModel { RedactionViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            modules(koinModule)
        }
    }

}