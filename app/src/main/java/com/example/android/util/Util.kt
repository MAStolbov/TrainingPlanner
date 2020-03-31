package com.example.android.util

object Util {

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

}