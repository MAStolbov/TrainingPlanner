package com.example.android.util


object SelectedTrainingDays {

    private lateinit var firstTrainingWeek:MutableMap<Int, Boolean>


    fun sendFirstTrainingWeek(firstWeek:MutableMap<Int, Boolean>){
        firstTrainingWeek = firstWeek
    }

    fun returnFirstTrainingWeek():MutableMap<Int,Boolean>{
        return firstTrainingWeek
    }
}