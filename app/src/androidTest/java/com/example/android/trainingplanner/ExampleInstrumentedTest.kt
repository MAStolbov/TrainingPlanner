package com.example.android.trainingplanner

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.database.TemplatesDatabase
import com.example.android.database.templateEntityDao.TemplatesDatabaseDAO
import com.example.android.database.templateEntityDao.TrainingTemplate
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var templateDao: TemplatesDatabaseDAO
    private lateinit var db: TemplatesDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, TemplatesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        templateDao = db.templateDatabaseDao
    }

    @After
    fun closeDb() {
        db.close()
    }

//    @Test
//    fun insertAndGetTemplate(){
//        val newTemplate = TrainingTemplate()
//
//        templateDao.insertTemplate(newTemplate)
//
//        val template = templateDao.getTemplate()
//        newTemplate.templateId = 1
//
//        assertEquals(newTemplate,template)
//    }

    @Test
    fun updateTemplate(){
        val newTemplate = TrainingTemplate()
        templateDao.insertTemplate(newTemplate)

        //newTemplate.templateId = 1
        newTemplate.numberOfTrainingWeeks = 1
        newTemplate.templateName = "TestName1"
        templateDao.updateTemplate(newTemplate)

        //newTemplate.numberOfTrainingWeeks = 2

        //val template = templateDao.getTemplate()

        //assertEquals(newTemplate,template)
    }


}
