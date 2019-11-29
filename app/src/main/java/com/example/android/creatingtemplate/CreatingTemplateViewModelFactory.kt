package com.example.android.creatingtemplate

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.database.TemplatesDatabase
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import javax.sql.DataSource

class CreatingTemplateViewModelFactory(
    private val dataSource: TemplatesDatabase,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreatingTemplateViewModel::class.java)) {
            return CreatingTemplateViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}