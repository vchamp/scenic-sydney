package com.epam.scenicsydney.location.edit

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.epam.scenicsydney.R

/**
 * Edit location fragment container.
 *
 * Listens to back navigation and sends the save check command to the view model.
 *
 * Reacts to the close command from the view model.
 */
class EditLocationActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_LOCATION_ID = "location_id"
        private const val EXTRA_IS_NEW = "is_new"

        fun getIntent(locationId: Long, isNew: Boolean, context: Context) =
                Intent(context, EditLocationActivity::class.java).apply {
                    putExtra(EXTRA_LOCATION_ID, locationId)
                    putExtra(EXTRA_IS_NEW, isNew)
                }
    }

    private val locationId: Long
        get() {
            val id = intent.getLongExtra(EXTRA_LOCATION_ID, -1)
            return if (id > 0) id else throw IllegalStateException("Location id is not specified in intent")
        }

    private val viewModel: EditLocationViewModel by lazy {
        ViewModelProviders.of(this,
                EditLocationViewModelFactory(locationId))
                .get(locationId.toString(), EditLocationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_location)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val isNew = intent.getBooleanExtra(EXTRA_IS_NEW, false)

        if (savedInstanceState == null) {
            val fragment = EditLocationFragment.newInstance(locationId, isNew)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment, EditLocationFragment.FRAGMENT_TAG)
                    .commit()
        }

        viewModel.getCloseCommand().observe(this, Observer { close ->
            if (close == true) {
                finish()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount == 0) {
                    viewModel.saveCheck()
                } else {
                    supportFragmentManager.popBackStack()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            viewModel.saveCheck()
        } else {
            super.onBackPressed()
        }
    }
}