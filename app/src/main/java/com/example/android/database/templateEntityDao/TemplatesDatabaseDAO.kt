package com.example.android.database.templateEntityDao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.database.templateEntityDao.TrainingTemplate


@Dao
interface TemplatesDatabaseDAO {

    @Insert
    fun insertTemplate(template: TrainingTemplate)

    @Update
    fun updateTemplate(template: TrainingTemplate)

    @Query("DELETE FROM training_templates_table")
    fun clearTemplateList()

    @Query("SELECT * from training_templates_table WHERE templateId= :key")
    fun get(key:Long): TrainingTemplate

    @Query("SELECT * from training_templates_table ORDER BY templateId DESC LIMIT 1")
    fun getTemplate(): TrainingTemplate?

    @Query("SELECT * FROM training_templates_table ORDER BY templateId DESC")
    fun getAllTemplates(): LiveData<List<TrainingTemplate>>
}