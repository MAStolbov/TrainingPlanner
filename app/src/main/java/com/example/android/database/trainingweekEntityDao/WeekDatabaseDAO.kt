package com.example.android.database.trainingweekEntityDao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface WeekDatabaseDAO{

    @Insert
    fun insertWeek(week:TrainingWeek?)

    @Insert
    fun insertWeeksList(weeksList:List<TrainingWeek>)

    @Update
    fun updateWeek(week: TrainingWeek)

    @Query("DELETE FROM training_weeks_table")
    fun clearWeek()

    @Query("DELETE FROM training_weeks_table WHERE weekId in (:keys)")
    fun deleteWeeks(keys: MutableList<Long>)

    @Query("SELECT * from training_weeks_table WHERE weekId= :key")
    fun getWeek(key:Long): TrainingWeek

    @Query("SELECT * FROM training_weeks_table WHERE parent_template_id= :key")
    fun getWeekForCurrentTemplate(key: Long): MutableList<TrainingWeek>

    @Query("DELETE FROM training_weeks_table WHERE parent_template_id= :key")
    fun deleteWeekForSpecificTemplate(key: Long)

    @Query("SELECT weekId from training_weeks_table ORDER BY weekId DESC LIMIT 1")
    fun getWeekMaxId(): Long?

    @Query("SELECT * FROM training_weeks_table ORDER BY weekId DESC")
    fun getAllWeeks(): LiveData<List<TrainingWeek>>
}