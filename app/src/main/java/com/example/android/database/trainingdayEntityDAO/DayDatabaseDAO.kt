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

    @Query("DELETE FROM training_days_table WHERE dayId in (:keys)")
    fun deleteDays(keys: MutableList<Long>)

    @Query("DELETE FROM training_days_table WHERE parent_template_id= :key")
    fun deleteDaysForSpecificTemplate(key: Long)

    @Query("SELECT * from training_days_table WHERE dayId= :key")
    fun getDay(key:Long): TrainingDay

    @Query("SELECT dayId from training_days_table ORDER BY dayId DESC LIMIT 1")
    fun getDayMaxId(): Long?

    @Query("SELECT * FROM training_days_table ORDER BY dayId DESC")
    fun getAllDays(): LiveData<List<TrainingDay>>

    @Query("SELECT * FROM training_days_table where parent_week_id= :key")
    fun getTrainingDaysForSpecificWeek(key: Long):MutableList<TrainingDay>

    @Query("SELECT * FROM training_days_table where parent_week_id in (:keys)")
    fun getTrainingDaysForAllWeek(keys: MutableList<Long>):MutableList<TrainingDay>
}