package com.example.android.util

import androidx.lifecycle.MutableLiveData

object Util {

    val endLoading = MutableLiveData<Boolean>()

    fun returnDayOfTheWeek(dayNumber:Int): String {
        return when (dayNumber) {
             1 -> "Monday"
             2 -> "Tuesday"
             3 -> "Wednesday"
             4 -> "Thursday"
             5 -> "Friday"
             6 -> "Saturday"
             7 -> "Sunday"
             else -> "Monday"
         }
    }

    fun getWeekNumber(key:Int):Int{
        return key / 10
    }

    fun getDayNumber(key:Int):Int{
        return key - ((key / 10) * 10)
    }
}