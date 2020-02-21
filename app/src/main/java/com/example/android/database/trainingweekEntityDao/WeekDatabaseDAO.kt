package com.example.android.database.trainingweekEntityDao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface WeekDatabaseDAO{

    @Insert
    fun insertWeek(week:TrainingWeek):Long

    @Update
    fun updateWeek(week: TrainingWeek)

    @Query("DELETE FROM training_weeks_table")
    fun clearWeek()

    @Query("SELECT * from training_weeks_table WHERE weekId= :key")
    fun getWeek(key:Long): TrainingWeek

    @Query("SELECT weekId from training_weeks_table ORDER BY weekId DESC LIMIT 1")
    fun getWeekMaxId(): Long?

    @Query("SELECT * FROM training_weeks_table ORDER BY weekId DESC")
    fun getAllWeeks(): LiveData<List<TrainingWeek>>
}