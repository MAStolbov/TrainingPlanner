package com.example.android.database.exerciseEntityDao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.database.trainingdayEntityDAO.TrainingDay


@Entity(tableName = "exercises_table")
data class Exercise(

    @PrimaryKey(autoGenerate = false)
    var exerciseId:Long = 0L,

    @ColumnInfo(name = "exercise_name")
    var exerciseName:String = "",

    @ColumnInfo(name = "parent_training_day_id")
    var parentTrainingDayId:Long = 0L,

    @ColumnInfo(name = "finishing")
    var finishing:Boolean = false,

    @ColumnInfo(name = "set")
    var set:String = "",

    @ColumnInfo(name = "rep")
    var rep:String = "",

    @ColumnInfo(name = "weight")
    var weight:String = ""
)