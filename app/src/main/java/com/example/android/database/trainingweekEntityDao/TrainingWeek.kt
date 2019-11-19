package com.example.android.database.trainingweekEntityDao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training_weeks_table")
data class TrainingWeek(

    @PrimaryKey(autoGenerate = true)
    var weekId:Long = 0L,

    @ColumnInfo(name = "parent_template_id")
    var parentTemplateId:Long = 0L,

    @ColumnInfo(name = "week_number")
    var weekNumber:Int = 0
)