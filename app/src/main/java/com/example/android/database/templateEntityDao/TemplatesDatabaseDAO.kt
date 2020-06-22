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

    @Query("DELETE FROM training_templates_table where templateId = :id")
    fun deleteTemplate(id:Long)

    @Query("DELETE FROM training_templates_table")
    fun deleteAllTemplate()

    @Query("SELECT * from training_templates_table WHERE templateId= :key")
    fun getTemplate(key:Long): TrainingTemplate

    /**
     * Return template's max ID
     */
    @Query("SELECT templateId from training_templates_table ORDER BY templateId DESC LIMIT 1")
    fun getTemplateMaxId():Long?

    @Query("SELECT * FROM training_templates_table ORDER BY templateId DESC")
    fun getAllTemplates(): LiveData<List<TrainingTemplate>>
}