package com.example.android.trainingplanner

import com.example.android.repository.Repository
import com.example.android.util.TemporaryDataStorageClass
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MockitoUnitTest {

    private lateinit var repository:Repository
    private lateinit var temporaryDataStorageClass: TemporaryDataStorageClass

    @Before
    fun createRepository(){
        repository = Mockito.mock(Repository::class.java)
    }

    @Before
    fun getDataStorageInstance(){
        temporaryDataStorageClass = TemporaryDataStorageClass.instance
    }

    @Test
    fun checkRepositoryMock(){
        val testId:Long? = 1
        Mockito.`when`(repository.returnMaxTemplateId()).thenReturn(testId)

        assertEquals(testId,repository.returnMaxTemplateId())
    }
}