package com.example.android.database.idStorageEntityDao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "id_storage_table")
data class IdStorageEntity(

    @PrimaryKey(autoGenerate = true)
    var storageId:Long = 0,

    @ColumnInfo(name = "template_id")
    var templateId:Long = 0,

    @ColumnInfo(name = "week_id")
    var weekId:Long = 0,

    @ColumnInfo(name = "day_id")
    var dayId:Long = 0,

    @ColumnInfo(name = "exercise_id")
    var exerciseId:Long = 0
)