package com.example.android.creatingtemplate

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO


class CreatingTemplateViewModel(dataSource: TemplatesDatabaseDAO, application: Application) : ViewModel(){

    val database = dataSource

    fun onSetTemplateName(name:String){

    }
}