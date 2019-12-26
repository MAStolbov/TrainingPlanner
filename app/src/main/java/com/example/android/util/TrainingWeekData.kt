package com.example.android.util

object TrainingWeekData {

    private var firstWeekId: Long = 0
    private var secondWeekId: Long = 0
    private var thirdWeekId: Long = 0
    private var fourthWeekId: Long = 0

    private var weekNumber: Int = 0
    private var dayNumber: Int = 0
    private var dayOfTheWeek: String = ""
    private var dayId:Long = 0

    fun sendDayAndNumberOfTheWeek(numberOfWeek: Int, day: Int) {
        weekNumber = numberOfWeek
        dayNumber = day
    }

    fun sendWeeksId(firstId: Long, secondId: Long, thirdId: Long, fourthId: Long) {
        firstWeekId = firstId
        secondWeekId = secondId
        thirdWeekId = thirdId
        fourthWeekId = fourthId
    }

    fun sendDayId(id:Long){
        dayId = id
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

    fun returnDayId():Long{
        return dayId
    }

    fun returnDayAndWeekNumber(): String {
        return "Week number:${weekNumber}, Week day:${returnDayOfTheWeek()} "
    }

    fun returnWeekId(weekNumber: Int): Long {
        return when (weekNumber) {
            1 -> firstWeekId
            2 -> secondWeekId
            3 -> thirdWeekId
            4 -> fourthWeekId
            else -> 0
        }
    }
}