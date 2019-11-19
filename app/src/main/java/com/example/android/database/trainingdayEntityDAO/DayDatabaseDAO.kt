package com.example.android.database.trainingdayEntityDAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface DayDatabaseDAO {

    @Insert
    fun insertDay(day: TrainingDay)

    @Update
    fun updateDay(day: TrainingDay)

    @Query("DELETE FROM training_days_table")
    fun clearDay()

    @Query("SELECT * from training_days_table WHERE dayId= :key")
    fun get(key:Long): TrainingDay

    @Query("SELECT * from training_days_table ORDER BY dayId DESC LIMIT 1")
    fun getDay(): TrainingDay?

    @Query("SELECT * FROM training_days_table ORDER BY dayId DESC")
    fun getAllDays(): LiveData<List<TrainingDay>>
}