package com.epam.scenicsydney.location

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.util.Log
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
class LocationsRepositoryRobolectricTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: LocationsRepository

    @Before
    fun setUp() {
        ShadowLog.stream = System.out

        val repositoryModule = RepositoryModule()
        Injector.APP_COMPONENT = DaggerAppComponent.builder()
                .appModule(TestAppModule(RuntimeEnvironment.application))
                .repositoryModule(repositoryModule)
                .build()

        repository = repositoryModule.provideLocationsRepository()
    }

    @After
    fun tearDown() {
        repository.close()
    }

    @Test
    fun defaultLocationsImport_allImported() {
        val locations = repository.getLocations().waitForValue { it != null && it.isNotEmpty() }
        assertNotNull("Locations not null", locations)
        assertEquals("Locations size", 5, locations?.size)
        locations?.forEach { Log.d("Test", "imported location: $it") }
    }

    @Test()
    fun sortDefaultLocationsByDistance_isCorrect() {
        val locations = repository.getLocations().waitForValue { it != null && it.isNotEmpty() }
                ?: emptyList()
        assertEquals("Locations size", 5, locations.size)
        val sorted = sortByDistanceFromCenter(locations)
        assertEquals("Sorted locations size", 5, sorted.size)
        assertEquals("Order", "Darling Harbour", sorted[0].name)
        assertEquals("Order", "Circular Quay", sorted[1].name)
        assertEquals("Order", "Milsons Point", sorted[2].name)
        assertEquals("Order", "Bondi Beach", sorted[3].name)
        assertEquals("Order", "Manly Beach", sorted[4].name)
    }
}
