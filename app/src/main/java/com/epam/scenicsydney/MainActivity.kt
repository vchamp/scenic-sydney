package com.epam.scenicsydney

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.epam.scenicsydney.location.edit.EditLocationActivity
import com.epam.scenicsydney.location.list.LocationsListFragment
import com.epam.scenicsydney.location.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Navigation {

    private companion object {
        const val FRAGMENT_TAG_MAP = "map"
        const val FRAGMENT_TAG_LIST = "list"
        const val STATE_LIST_MODE = "list_mode"
    }

    private var isListMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.map -> showMap()
                R.id.list -> showList()
            }
            true
        }

        if (savedInstanceState?.getBoolean(STATE_LIST_MODE) == true) {
            navigationView.selectedItemId = R.id.list
//            showList()
        } else {
            navigationView.selectedItemId = R.id.map
//            showMap()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_LIST_MODE, isListMode)
    }

    private fun showMap() {
        isListMode = false
        val existingFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MAP)
        if (existingFragment != null) {
            val transaction = supportFragmentManager.beginTransaction().show(existingFragment)
            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_LIST)?.let {
                transaction.hide(it)
            }
            transaction.commit()
        } else {
            val mapFragment = MapFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, mapFragment, FRAGMENT_TAG_MAP).commit()
        }
    }

    private fun showList() {
        isListMode = true
        val existingFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_LIST)
        if (existingFragment != null) {
            val transaction = supportFragmentManager.beginTransaction().show(existingFragment)
            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_MAP)?.let {
                transaction.hide(it)
            }
            transaction.commit()
        } else {
            val listFragment = LocationsListFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, listFragment, FRAGMENT_TAG_LIST).commit()
        }
    }

    override fun openEditLocation(locationId: Long, isNew: Boolean) {
        if (locationId > 0) {
            startActivity(EditLocationActivity.getIntent(locationId, isNew, this))
        }
    }
}
