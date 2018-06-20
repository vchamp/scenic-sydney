package com.epam.scenicsydney.location

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.epam.scenicsydney.inject.DaggerAppComponent
import com.epam.scenicsydney.inject.Injector
import com.epam.scenicsydney.inject.RepositoryModule
import com.epam.scenicsydney.inject.TestAppModule
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

    @Before
    fun setUp() {
        ShadowLog.stream = System.out

        val repositoryModule = RepositoryModule()
        Injector.APP_COMPONENT = DaggerAppComponent.builder()
                .appModule(TestAppModule(RuntimeEnvironment.application))
                .repositoryModule(repositoryModule)
                .build()

        viewModel = LocationsViewModel()

        assertNotNull("Repository injected", viewModel.repository)
    }

    @After
    fun tearDown() {
        viewModel.repository.close()
    }

    @Test
    fun addLocation_isAdded() {
        viewModel.addLocation(4.0, 4.0)
        val locations = viewModel.getLocations().waitForValue { it != null && it.size == 6 }
        assertEquals("Locations number increased", 6, locations?.size)
    }
}