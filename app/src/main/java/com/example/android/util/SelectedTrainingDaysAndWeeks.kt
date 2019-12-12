package com.example.android.util


object SelectedTrainingDaysAndWeeks {

    private lateinit var firstTrainingWeek:MutableMap<Int, Boolean>
    private lateinit var secondTrainingWeek:MutableMap<Int, Boolean>
    private lateinit var thirdTrainingWeek:MutableMap<Int, Boolean>
    private lateinit var fourthTrainingWeek:MutableMap<Int, Boolean>
    private var numberOfWeeks:Int = 0

    fun sendNumberOfWeeks(number:Int){
        numberOfWeeks = number
    }

    fun sendSelectedDays(week1:MutableMap<Int, Boolean>,week2:MutableMap<Int, Boolean>,
                         week3:MutableMap<Int, Boolean>,week4:MutableMap<Int, Boolean>){
        firstTrainingWeek = week1
        secondTrainingWeek = week2
        thirdTrainingWeek = week3
        fourthTrainingWeek = week4

    }

    fun returnNumberOfWeeks():Int{
        return numberOfWeeks
    }

    fun returnFirstTrainingWeek():MutableMap<Int,Boolean>{
        return firstTrainingWeek
    }
    fun returnSecondtTrainingWeek():MutableMap<Int,Boolean>{
        return secondTrainingWeek
    }
    fun returnThirdTrainingWeek():MutableMap<Int,Boolean>{
        return thirdTrainingWeek
    }
    fun returnFourthTrainingWeek():MutableMap<Int,Boolean>{
        return fourthTrainingWeek
    }
}