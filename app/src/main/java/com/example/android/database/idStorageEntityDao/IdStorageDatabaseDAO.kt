package com.example.android.database.idStorageEntityDao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface IdStorageDatabaseDAO {


    @Insert
    fun insert(idStorage:IdStorageEntity)

    @Update
    fun update(idStorage: IdStorageEntity)

    @Query("SELECT template_id FROM id_storage_table ORDER BY template_id DESC LIMIT 1")
    fun returnMaxTemplateId():Long?

    @Query("SELECT week_id FROM id_storage_table ORDER BY week_id DESC LIMIT 1")
    fun returnMaxWeekId():Long?

    @Query("SELECT day_id FROM id_storage_table ORDER BY day_id DESC LIMIT 1")
    fun returnMaxDayId():Long?

    @Query("SELECT exercise_id FROM id_storage_table ORDER BY exercise_id DESC LIMIT 1")
    fun returnMaxExerciseId():Long?

    @Query("DELETE FROM id_storage_table")
    fun clearIdStorage()
}