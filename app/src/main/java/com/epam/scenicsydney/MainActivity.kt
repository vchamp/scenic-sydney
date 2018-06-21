package com.epam.scenicsydney

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.epam.scenicsydney.location.edit.EditLocationActivity
import com.epam.scenicsydney.location.list.LocationsListFragment
import com.epam.scenicsydney.location.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main activity of the application. Contains map and locations list fragments.
 */
class MainActivity : AppCompatActivity(), Navigation {

    private companion object {
        const val FRAGMENT_TAG_MAP = "map"
        const val FRAGMENT_TAG_LIST = "list"
        const val SELECTED_NAV_ITEM_ID = "selected_nav_item_id"
    }

    private var selectedNavItemId = R.id.map

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView.setOnNavigationItemSelectedListener {
            selectedNavItemId = it.itemId
            when (it.itemId) {
                R.id.map -> showMap()
                R.id.list -> showList()
            }
            true
        }

        navigationView.selectedItemId = savedInstanceState?.getInt(SELECTED_NAV_ITEM_ID, R.id.map) ?: R.id.map
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_NAV_ITEM_ID, selectedNavItemId)
    }

    private fun showMap() {
        switchFragments(FRAGMENT_TAG_MAP, FRAGMENT_TAG_LIST, ::MapFragment)
    }

    private fun showList() {
        switchFragments(FRAGMENT_TAG_LIST, FRAGMENT_TAG_MAP, ::LocationsListFragment)
    }

    private fun switchFragments(showFragmentTag: String, hideFragmentTag: String, newFragmentConstructor: () -> Fragment) {
        val existingFragment = supportFragmentManager.findFragmentByTag(showFragmentTag)
        if (existingFragment != null) {
            val transaction = supportFragmentManager.beginTransaction().show(existingFragment)
            supportFragmentManager.findFragmentByTag(hideFragmentTag)?.let {
                transaction.hide(it)
            }
            transaction.commit()
        } else {
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, newFragmentConstructor(), showFragmentTag).commit()
        }
    }

    override fun openEditLocation(locationId: Long, isNew: Boolean) {
        if (locationId > 0) {
            startActivity(EditLocationActivity.getIntent(locationId, isNew, this))
        }
    }
}
