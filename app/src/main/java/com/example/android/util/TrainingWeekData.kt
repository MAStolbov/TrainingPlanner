package com.example.android.util

object TrainingWeekData {


    private var weekNumber: Int = 0
    private var dayNumber: Int = 0
    private var dayOfTheWeek: String = ""

    fun sendDayAndNumberOfTheWeek(numberOfWeek: Int, day: Int) {
        weekNumber = numberOfWeek
        dayNumber = day
    }


    fun returnWeekNumber(): Int {
        return weekNumber
    }

    fun returnDayOfTheWeek(): String {
        dayOfTheWeek = when (dayNumber) {
            1 -> "Monday"
            2 -> "Tuesday"
            3 -> "Wednesday"
            4 -> "Thursday"
            5 -> "Friday"
            6 -> "Saturday"
            7 -> "Sunday"
            else -> "Monday"
        }
        return dayOfTheWeek
    }


    fun returnDayAndWeekNumber(): String {
        return "Week number:${weekNumber}, Week day:${returnDayOfTheWeek()} "
    }

}