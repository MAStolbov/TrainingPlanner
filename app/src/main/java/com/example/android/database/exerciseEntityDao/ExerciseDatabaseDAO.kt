package com.example.android.database.exerciseEntityDao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.database.trainingweekEntityDao.TrainingWeek


@Dao
interface ExerciseDatabaseDAO{

    @Insert
    fun insertExercise(exercise: Exercise)

    @Update
    fun updateExercise(exercise: Exercise)

    @Query("DELETE FROM exercises_table")
    fun clearExercise()

    @Query("SELECT * from exercises_table WHERE exerciseId= :key")
    fun get(key:Long): Exercise

    @Query("SELECT * from exercises_table ORDER BY exerciseId DESC LIMIT 1")
    fun getExercise(): Exercise?

    @Query("SELECT * FROM exercises_table ORDER BY exerciseId DESC")
    fun getAllExercises(): LiveData<List<Exercise>>
}