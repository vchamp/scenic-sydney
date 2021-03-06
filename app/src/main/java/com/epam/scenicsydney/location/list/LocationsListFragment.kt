package com.epam.scenicsydney.location.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.epam.scenicsydney.R
import com.epam.scenicsydney.location.*
import com.epam.scenicsydney.model.Location
import kotlinx.android.synthetic.main.item_location.view.*

/**
 * Simple fragment with locations list sorted by distance from Sydney center.
 */
class LocationsListFragment : Fragment() {

    // activity scope allows to reuse the same view model for map and list fragments
    private val viewModel: LocationsViewModel by lazy {
        val activity = activity ?: throw IllegalStateException("Not attached")
        ViewModelProviders.of(activity, LocationsViewModelFactory()).get(LocationsViewModel::class.java)
    }

    private val navigation: Navigation
        get() = if (activity is Navigation) {
            activity as Navigation
        } else {
            throw ClassCastException("Activity must implement Navigation")
        }

    private val recyclerView: RecyclerView
        get() = view as RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_locations_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getLocations().observe(this, Observer {
            val locations = it ?: emptyList()
            recyclerView.adapter = LocationsAdapter(sortByDistanceFromCenter(locations))
        })
    }

    inner class LocationsAdapter(private val mLocations: List<Location>) : RecyclerView.Adapter<ViewHolder>() {
        override fun getItemCount(): Int = mLocations.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val location = mLocations[position]
            holder.setLocationId(location.id)
            holder.setTitle(if (!location.name.isBlank()) location.name else "Enter title")
            holder.setDistance("%.2f km".format(getDistanceToCenter(location) / 1000))
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var locationId: Long? = null

        private val titleTextView: TextView = itemView.titleTextView
        private val distanceTextView: TextView = itemView.distanceTextView

        init {
            itemView.setOnClickListener {
                locationId?.let {
                    navigation.openEditLocation(it, false)
                }
            }
        }

        fun setLocationId(id: Long) {
            locationId = id
        }

        fun setTitle(title: String) {
            titleTextView.text = title
        }

        fun setDistance(distance: String) {
            distanceTextView.text = distance
        }
    }
}