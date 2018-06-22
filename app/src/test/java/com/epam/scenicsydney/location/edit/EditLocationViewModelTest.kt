package com.epam.scenicsydney.location.edit

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.epam.scenicsydney.location.LocationsRepository
import com.epam.scenicsydney.model.Location
import com.epam.scenicsydney.uninitialized
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EditLocationViewModelTest {

    companion object {
        const val MOCK_LOCATION_ID = 1L
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var locationsRepository: LocationsRepository

    private lateinit var viewModel: EditLocationViewModel

    // observed location
    private lateinit var location: Location
    private val locationObserver = Observer<Location> {
        location = it ?: uninitialized()
    }

    @Before
    fun setUp() {
        // mock repository data
        val location = Location(MOCK_LOCATION_ID, 0.0, 0.0, "")
        val locationLiveData = MutableLiveData<Location>().apply {
            value = location
        }

        locationsRepository = Mockito.mock(LocationsRepository::class.java)

        Mockito.`when`(locationsRepository.getLocation(MOCK_LOCATION_ID)).thenReturn(locationLiveData)

        viewModel = EditLocationViewModel(locationsRepository)
    }

    @Test(expected = IllegalStateException::class)
    fun getLocationWhenIdIsNotSet_isError() {
        viewModel.getLocation()
    }

    @Test
    fun saveTitle_updatesLocation() {
        val testTitle = "Test"
        viewModel.locationId = MOCK_LOCATION_ID
        viewModel.getLocation().observeForever(locationObserver)
        viewModel.save(testTitle)
        assertEquals("Title is same", testTitle, location.name)
        viewModel.getLocation().removeObserver(locationObserver)
    }
}