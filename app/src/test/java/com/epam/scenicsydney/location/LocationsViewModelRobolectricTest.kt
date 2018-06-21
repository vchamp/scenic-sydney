package com.epam.scenicsydney.location

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.epam.scenicsydney.database.createInMemoryDatabase
import com.epam.scenicsydney.waitForValue
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
class LocationsViewModelRobolectricTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LocationsViewModel

    private lateinit var repository: LocationsRepository

    @Before
    fun setUp() {
        ShadowLog.stream = System.out

        val context = RuntimeEnvironment.application.applicationContext
        val database = createInMemoryDatabase(context)

        repository = LocationsRepository(database)

        viewModel = LocationsViewModel(repository)

        assertNotNull("Repository injected", repository)
    }

    @After
    fun tearDown() {
        repository.close()
    }

    @Test
    fun addLocation_isAdded() {
        viewModel.addLocation(4.0, 4.0)
        val locations = viewModel.getLocations().waitForValue { it != null && it.size == 6 }
        assertEquals("Locations number increased", 6, locations?.size)
    }
}