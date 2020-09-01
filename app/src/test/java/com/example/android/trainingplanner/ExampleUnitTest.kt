package com.example.android.trainingplanner



import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(24))
class ExampleUnitTest {
    private lateinit var temporaryDataStorageClass: TemporaryDataStorageClass
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun getDataStorageInstance(){
        temporaryDataStorageClass = TemporaryDataStorageClass.instance
    }

    @Test
    fun repository_isCreated(){
        val repository = Repository.getRepositoryInstance(context)
    }


    @Test
    fun exercises_isCreated() {

        val exerciseName = "test"
        val set = "2"
        val rep = "3"
        val weight = "20"
        val weekNumber = 2
        val dayNumber = 1
        val localId = 1

        val fakeExercise = Exercise(
            exerciseName = exerciseName,
            set = set,
            rep = rep,
            weight = weight,
            weekNumber = weekNumber,
            dayNumber = dayNumber,
            localId = localId
        )

        temporaryDataStorageClass.currentTrainingDay.apply {
            this.weekNumber =  weekNumber
            this.dayNumber = dayNumber
        }

        temporaryDataStorageClass.createNewExercise(exerciseName, set, rep, weight)

        assertEquals(fakeExercise,temporaryDataStorageClass.exercisesList.last())

//        assertThat(fakeExercise,not(temporaryDataStorageClass.exercisesList.last()))
    }

    @Test
    fun returnExerciseListForSpecificDay_isTrue(){

        val secondWSecondDExerciseOne = Exercise(1L,weekNumber = 2,dayNumber = 2)
        val secondWSecondDExerciseTwo = Exercise(2L,weekNumber = 2,dayNumber = 2)
        val secondWThirdDExerciseOne = Exercise(3L,weekNumber = 2,dayNumber = 3)

        val fakeExerciseList = listOf(secondWSecondDExerciseOne,secondWSecondDExerciseTwo)

        temporaryDataStorageClass.exercisesList.apply {
            add(secondWSecondDExerciseOne)
            add(secondWSecondDExerciseTwo)
            add(secondWThirdDExerciseOne)
        }

        assertEquals( fakeExerciseList,temporaryDataStorageClass.returnExerciseListForSpecificDay(2,2))
    }


    @After
    fun clearData(){
        temporaryDataStorageClass.exercisesList.clear()
    }
}
