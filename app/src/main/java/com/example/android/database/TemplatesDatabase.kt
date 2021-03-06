package com.example.android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.database.exerciseEntityDao.ExerciseDatabaseDAO
import com.example.android.database.idStorageEntityDao.IdStorageDatabaseDAO
import com.example.android.database.idStorageEntityDao.IdStorageEntity
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.database.trainingdayEntityDAO.DayDatabaseDAO
import com.example.android.database.trainingdayEntityDAO.TrainingDay
import com.example.android.database.trainingweekEntityDao.TrainingWeek
import com.example.android.database.trainingweekEntityDao.WeekDatabaseDAO

@Database(
    entities = [TrainingTemplate::class, TrainingWeek::class, TrainingDay::class, Exercise::class,
    IdStorageEntity::class], version = 14, exportSchema = false
)
abstract class TemplatesDatabase : RoomDatabase() {
    abstract val templateDatabaseDao: TemplatesDatabaseDAO
    abstract val trainingWeekDao: WeekDatabaseDAO
    abstract val trainingDayDao: DayDatabaseDAO
    abstract val exerciseDao: ExerciseDatabaseDAO
    abstract val idStorageDao: IdStorageDatabaseDAO

    companion object {

        @Volatile
        private var INSTANCE: TemplatesDatabase? = null

        fun getInstance(context: Context): TemplatesDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, TemplatesDatabase::class.java,
                        "training_templates_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}