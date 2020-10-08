package com.example.android.trainingplanner

import android.app.Application
import com.example.android.repository.Repository
import com.example.android.trainigtemplatelist.TrainingTemplatesListViewModel
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Koin : Application() {

    private val koinModule = module {
        single { Repository.getRepositoryInstance(baseContext) }
        viewModel { TrainingTemplatesListViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            modules(koinModule)
        }
    }

}