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

    @Query("DELETE FROM exercises_table WHERE exerciseId in(:keys)")
    fun deleteExercises(keys: MutableList<Long>)

    @Query("DELETE FROM exercises_table WHERE parent_template_id= :key")
    fun deleteExercisesForSpecificTemplate(key: Long)

    @Query("SELECT * from exercises_table WHERE exerciseId= :key")
    fun getExercise(key:Long): Exercise

    @Query("SELECT exerciseId from exercises_table ORDER BY exerciseId DESC LIMIT 1")
    fun getExerciseMaxId(): Long?

    @Query("SELECT * FROM exercises_table ORDER BY exerciseId DESC")
    fun getAllExercises(): LiveData<List<Exercise>>

    @Query("SELECT * FROM exercises_table where parent_training_day_id= :key")
    fun getExercisesForSpecificDay(key: Long):MutableList<Exercise>

    @Query("SELECT * FROM exercises_table where parent_training_day_id in (:keys)")
    fun getExercisesForAllDays(keys:MutableList<Long>):MutableList<Exercise>
}