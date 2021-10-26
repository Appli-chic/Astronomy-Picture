package com.applichic.astronomypicture.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.applichic.astronomypicture.MainActivity
import com.applichic.astronomypicture.R
import com.applichic.astronomypicture.utils.ViewAction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EntryDetailFragmentTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Test
    fun jumpToEntryDetail() {
        onView(withId(R.id.recycler_view_photos)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewAction.clickChildViewWithId(R.id.image_grid_entry)
            )
        )

        // Check if the page is loaded
        onView(withId(R.id.title))
            .check(ViewAssertions.matches(isDisplayed()))
    }
}