package com.example.android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import com.example.android.database.templateEntityDao.TrainingTemplate

@Database(entities = [TrainingTemplate::class], version = 1, exportSchema = false)
abstract class TemplatesDatabase : RoomDatabase() {
    abstract val templateDatabaseDao: TemplatesDatabaseDAO

    companion object {

        @Volatile
        private var INSTANCE: TemplatesDatabase? = null

        fun getInstance(context: Context): TemplatesDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, TemplatesDatabase::class.java,
                        "training_templates_database")
                            .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                            .build()
                        INSTANCE = instance
                }

                return instance
            }
        }
    }
}