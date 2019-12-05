package com.example.android.creatingtrainingday

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.database.TemplatesDatabase


class CreatingTrainingDayViewModel (dataSours:TemplatesDatabase, application: Application):ViewModel(){
    val database = dataSours
}