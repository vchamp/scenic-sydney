package com.epam.scenicsydney.location.list

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.epam.scenicsydney.MainActivity
import com.epam.scenicsydney.R
import com.epam.scenicsydney.inject.AndroidTestAppModule
import com.epam.scenicsydney.inject.DaggerAppComponent
import com.epam.scenicsydney.inject.Injector
import com.epam.scenicsydney.location.edit.EditLocationFragment
import org.hamcrest.Matchers
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationsListFragmentTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            val appContext = InstrumentationRegistry.getTargetContext()
            assertEquals("com.epam.scenicsydney", appContext.packageName)
            Injector.APP_COMPONENT = DaggerAppComponent.builder()
                    .appModule(AndroidTestAppModule(appContext))
                    .build()
        }
    }

    @Test
    fun defaultLocations_areShown() {
        // initially map is shown
        onView(withClassName(Matchers.containsString("RecyclerView"))).check(doesNotExist())
        // open list
        onView(withId(R.id.list)).perform(click())
        // check that list is shown
        onView(withClassName(Matchers.containsString("RecyclerView"))).check(matches(isDisplayed()))
        // list must have exactly 5 default locations
        onView(withClassName(Matchers.containsString("RecyclerView"))).check(matches(hasChildCount(5)))
    }

    @Test
    fun clickOnLocation_detailsAreShown() {
        val locationName = "Milsons Point"
        // open list
        onView(withId(R.id.list)).perform(click())
        // click on the first item
        onView(withClassName(Matchers.containsString("RecyclerView"))).perform(actionOnItem<EditLocationFragment.NoteViewHolder>(hasDescendant(withText(locationName)), click()))
        // check that details are shown and title is correct
        onView(withId(R.id.titleEditText)).check(matches(isDisplayed())).check(matches(withText(locationName)))
    }
}
