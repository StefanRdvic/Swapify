package com.yavs.swapify

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yavs.swapify.ui.MainActivity
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun checkSettings() {
        onView(withId(R.id.settingsButton))
            .perform(click())

        onView(withId(R.id.userRecycler))
            .perform(RecyclerViewActions.scrollToPosition<ViewHolder>(0))
            .check(matches(hasDescendant(withText("LOG INTO SPOTIFY"))))
        onView(withId(R.id.userRecycler))
            .perform(RecyclerViewActions.scrollToPosition<ViewHolder>(1))
            .check(matches(hasDescendant(withText("LOG INTO DEEZER"))))
        onView(withId(R.id.userRecycler))
            .perform(RecyclerViewActions.scrollToPosition<ViewHolder>(2))
            .check(matches(hasDescendant(withText("LOG INTO SOUNDCLOUD"))))
    }

    @Test
    fun checkSwap(){
        onView(withId(R.id.fromSpinner)).check(matches(withSpinnerText("Spotify")))
        onView(withId(R.id.fromSpinner)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.fromSpinner)).check(matches(withSpinnerText("Deezer")))
        onView(withId(R.id.fromSpinner)).perform(click())
        onData(anything()).atPosition(2).perform(click())
        onView(withId(R.id.fromSpinner)).check(matches(withSpinnerText("SoundCloud")))
        onView(withId(R.id.toSpinner)).check(matches(withSpinnerText("Deezer")))
        onView(withId(R.id.toSpinner)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.toSpinner)).check(matches(withSpinnerText("Spotify")))
        onView(withId(R.id.toSpinner)).perform(click())
        onData(anything()).atPosition(2).perform(click())
        onView(withId(R.id.toSpinner)).check(matches(withSpinnerText("SoundCloud")))
        onView(withId(R.id.swapButton)).check(matches(not(isEnabled())))
    }

}