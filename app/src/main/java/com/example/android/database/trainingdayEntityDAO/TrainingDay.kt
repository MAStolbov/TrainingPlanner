package com.example.android.database.trainingdayEntityDAO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "training_days_table")
data class TrainingDay(

    @PrimaryKey(autoGenerate = true)
    var dayId:Long = 0L,

    @ColumnInfo(name = "week_number")
    var weekNumber:Int = 0,

    @ColumnInfo(name = "day_number")
    var dayNumber: Int = 0,

    @ColumnInfo(name = "parent_week_id")
    var parentWeekId:Long = 0L,

    @ColumnInfo(name = "parent_template_id")
    var parentTemplateId:Long = 0L,

    @ColumnInfo(name = "day_of_the_week")
    var dayOfTheWeek:String = ""
)