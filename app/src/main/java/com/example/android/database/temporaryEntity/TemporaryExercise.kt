package com.example.android.database.temporaryEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "temporary_exercises_table")
data class TemporaryExercise(

    @PrimaryKey(autoGenerate = true)
    var exerciseId:Long = 0L,

    @ColumnInfo(name = "exercise_name")
    var exerciseName:String = "",

    @ColumnInfo(name = "set")
    var set:String = "",

    @ColumnInfo(name = "rep")
    var rep:String = "",

    @ColumnInfo(name = "weight")
    var weight:String = ""
)