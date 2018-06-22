package com.epam.scenicsydney.location

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.epam.scenicsydney.any
import com.epam.scenicsydney.model.Location
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocationsViewModelTest {

    companion object {
        const val INITIAL_LOCATIONS_COUNT = 3
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var locationsRepository: LocationsRepository

    private lateinit var viewModel: LocationsViewModel

    // observed list
    private lateinit var locations: List<Location>
    private val observer = Observer<List<Location>> {
        locations = it ?: emptyList()
    }

    @Before
    fun setUp() {
        // changed by mock repository
        val testLocations = Array(INITIAL_LOCATIONS_COUNT) {
            Location(it.toLong(), it.toDouble(), it.toDouble(), "Location $it")
        }.toMutableList()

        // returned by mock repository
        val locationsLiveData = MutableLiveData<List<Location>>().apply {
            value = testLocations
        }

        locationsRepository = mock(LocationsRepository::class.java)

        Mockito.`when`(locationsRepository.getLocations()).thenReturn(locationsLiveData)
        Mockito.`when`(locationsRepository.addLocation(any())).then {
            testLocations.add(it.arguments[0] as Location)
        }

        viewModel = LocationsViewModel(locationsRepository)
        viewModel.getLocations().observeForever(observer)
    }

    @After
    fun tearDown() {
        viewModel.getLocations().removeObserver(observer)
    }

    @Test
    fun getLocations_isCorrectCount() {
        assertEquals("Locations count", INITIAL_LOCATIONS_COUNT, locations.size)
    }

    @Test
    fun addLocation_isAddedLocationRemembered() {
        viewModel.addLocation(0.0, 0.0)
        val addedLocation = viewModel.getAddedLocation()
        assertNotNull("Added location is not null", addedLocation)
    }

    @Test
    fun addLocation_addedLocationCorrect() {
        val lat = INITIAL_LOCATIONS_COUNT + 1.toDouble()
        val lng = lat + 1
        viewModel.addLocation(lat, lng)
        val addedLocation = viewModel.getAddedLocation()
        assertEquals("Added location latitude", lat, addedLocation?.latitude)
        assertEquals("Added location longitude", lng, addedLocation?.longitude)
        assertEquals("Added location name is empty", "", addedLocation?.name)
    }

    @Test
    fun addLocation_isAdded() {
        val sizeBefore = locations.size
        assertEquals("Initial locations count", INITIAL_LOCATIONS_COUNT, sizeBefore)
        viewModel.addLocation(0.0, 0.0)
        verify(locationsRepository).addLocation(any())
        assertEquals("New locations count", sizeBefore + 1, locations.size)
    }

    @Test
    fun removeAddedLocation_isRemoved() {
        viewModel.addLocation(0.0, 0.0)
        val addedLocation = viewModel.getAddedLocation()
        assertNotNull("Added location is not null", addedLocation)
        viewModel.removeAddedLocation()
        assertNull("Added location is null after remove", viewModel.getAddedLocation())
    }
}