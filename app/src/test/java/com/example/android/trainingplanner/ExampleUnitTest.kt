package com.example.android.trainingplanner

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.util.TemporaryDataStorageClass
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var temporaryDataStorageClass: TemporaryDataStorageClass

    @Before
    fun getDataStorageInstance(){
        temporaryDataStorageClass = TemporaryDataStorageClass.instance
    }

    @Test
    fun exercises_isCreated() {
        val fakeExercise = Exercise(
            exerciseName = "test",
            set = "2",
            rep = "3",
            weight = "20",
            weekNumber = 2,
            dayNumber = 1,
            localId = 1
        )

        temporaryDataStorageClass.currentTrainingDay.apply {
            weekNumber = 2
            dayNumber = 1
        }

        temporaryDataStorageClass.createNewExercise("test", "2", "3", "20")

        assertEquals(fakeExercise,temporaryDataStorageClass.exercisesList.last())
    }

    @Test
    fun returnExerciseListForSpecificDay_isTrue(){
        val fakeExerciseList = listOf(Exercise(1L,weekNumber = 2,dayNumber = 2),Exercise(2L,weekNumber = 2,dayNumber = 2))

        temporaryDataStorageClass.exercisesList.apply {
            add(Exercise(1L,weekNumber = 2,dayNumber = 2))
            add(Exercise(2L,weekNumber = 2,dayNumber = 2))
            add(Exercise(3L,weekNumber = 2,dayNumber = 3))
        }

        assertEquals( fakeExerciseList,temporaryDataStorageClass.returnExerciseListForSpecificDay(2,2))
    }
}
