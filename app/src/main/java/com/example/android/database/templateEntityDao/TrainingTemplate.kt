package com.example.android.database.templateEntityDao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "training_templates_table")
data class TrainingTemplate(

    @PrimaryKey(autoGenerate = true)
    var templateId:Long = 0L,

    @ColumnInfo(name = "template_name")
    var templateName:String = "",

    @ColumnInfo(name = "template_description")
    var templateDescription:String = "",

    @ColumnInfo(name = "number_of_training_weeks")
    var numberOfTrainingWeeks:Int = 0
)