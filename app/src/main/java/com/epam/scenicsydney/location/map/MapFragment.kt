package com.epam.scenicsydney.location.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.epam.scenicsydney.Navigation
import com.epam.scenicsydney.R
import com.epam.scenicsydney.location.LocationsViewModel
import com.epam.scenicsydney.location.LocationsViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*

/**
 * Wrapper fragment for SupportMapFragment. Sets default map settings. Handles all user interaction with the map.
 * Observes the locations list and updates markers accordingly.
 */
class MapFragment : Fragment(), OnMapReadyCallback {

    private companion object {
        val DEFAULT_MAP_CENTER = LatLng(-33.84, 151.25)
        const val MIN_ZOOM = 8f
        const val MAX_ZOOM = 16f
        const val DEFAULT_ZOOM = 12f
    }

    private lateinit var map: GoogleMap

    // activity scope allows to reuse the same view model for the map and list fragments
    private val viewModel: LocationsViewModel by lazy {
        val activity = activity ?: throw IllegalStateException("Not attached")
        ViewModelProviders.of(activity, LocationsViewModelFactory()).get(LocationsViewModel::class.java)
    }

    private val navigation: Navigation
        get() =
            if (activity is Navigation) {
                activity as Navigation
            } else throw ClassCastException("Activity must implement Navigation")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            viewModel.setAddLocationMode(false)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap.apply {
            setMinZoomPreference(MIN_ZOOM)
            setMaxZoomPreference(MAX_ZOOM)

            moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_MAP_CENTER))
            moveCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM))

            uiSettings.isMapToolbarEnabled = false

            setOnMapClickListener { coords ->
                if (viewModel.isAddLocationMode()) {
                    viewModel.addLocation(coords.latitude, coords.longitude)
                }
            }

            setOnInfoWindowClickListener { marker ->
                navigation.openEditLocation(marker.tag as Long, false)
            }
        }

        viewModel.getLocations().observe(this, Observer { locations ->
            if (locations != null) {
                map.clear()
                locations.forEach { location ->
                    val marker = map.addMarker(MarkerOptions()
                            .position(LatLng(location.latitude, location.longitude))
                            .title(if (!location.name.isBlank()) location.name else "Enter title"))
                    marker.tag = location.id
                }

                val addedLocation = viewModel.getAddedLocation()
                if (addedLocation != null) {
                    viewModel.setAddLocationMode(false)
                    viewModel.removeAddedLocation()
                    navigation.openEditLocation(addedLocation.id, true)
                }
            }
        })

        floatingActionButton.visibility = VISIBLE
        floatingActionButton.setOnClickListener { viewModel.switchAddLocationMode() }
        viewModel.getAddLocationMode().observe(this, Observer { adding ->
            if (adding != null && adding) {
                Toast.makeText(context, R.string.click_to_add_location, LENGTH_LONG).show()
                floatingActionButton.setImageResource(R.drawable.ic_clear)
            } else {
                floatingActionButton.setImageResource(R.drawable.ic_add)
            }
        })
    }
}