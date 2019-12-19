package com.example.android.database.temporaryEntity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TemporaryExerciseDatabaseDAO{
    @Insert
    fun insertExercise(exercise: TemporaryExercise)

    @Update
    fun updateExercise(exercise: TemporaryExercise)

    @Query("DELETE FROM temporary_exercises_table")
    fun clearExercise()

    @Query("SELECT * from temporary_exercises_table WHERE exerciseId= :key")
    fun get(key:Long): TemporaryExercise

    @Query("SELECT exerciseId from temporary_exercises_table ORDER BY exerciseId DESC LIMIT 1")
    fun getExerciseMaxId(): Long?

    @Query("SELECT * FROM temporary_exercises_table ORDER BY exerciseId DESC")
    fun getAllExercises(): LiveData<List<TemporaryExercise>>
}