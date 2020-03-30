package com.example.android.util

object Util {

    var createdTrainingDays:MutableMap<Map<Int,Int>,Boolean> = mutableMapOf()
    var buttonNumber:Int = 0
    var weekNumber:Int = 0
    var dayNumber:Int = 0
    var dayOfTheWeek:String = ""

    fun setWeekAndDayNumber(numberForWeek:Int, numberForDay:Int){
        weekNumber = numberForWeek
        dayNumber = numberForDay
        dayOfTheWeek = returnDayOfTheWeek(numberForDay)
    }

    fun returnWeekNumber():Int{
        return weekNumber
    }

    fun returnDayNumber():Int{
        return dayNumber
    }

    fun dayExistenceCheck(keyForMap:Map<Int,Int>):Boolean{
        return createdTrainingDays.containsKey(keyForMap)
    }

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