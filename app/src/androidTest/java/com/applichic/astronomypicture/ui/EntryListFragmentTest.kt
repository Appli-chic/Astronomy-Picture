package com.applichic.astronomypicture.ui

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.applichic.astronomypicture.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


import androidx.test.espresso.Espresso.onView

import com.applichic.astronomypicture.R
import com.applichic.astronomypicture.utils.RecyclerViewItemCountAssertion
import com.applichic.astronomypicture.utils.atPosition
import java.util.*


@RunWith(AndroidJUnit4::class)
class EntryListFragmentTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Test
    fun onDisplayingEntries() {
        // Check if the list of entries is displayed
        onView(withId(R.id.recycler_view_photos))
            .check(matches(isDisplayed()))

        // Check if the first element of the list is displayed
        onView(withId(R.id.recycler_view_photos))
            .check(matches(atPosition(0, withId(R.id.image_grid_entry))))

        // Check the amount of entries in the list
        val today = Calendar.getInstance()
        var nbDays = today.get(Calendar.DAY_OF_MONTH)
        today.add(Calendar.MONTH, -1)
        nbDays += today.getActualMaximum(Calendar.DATE)

        onView(withId(R.id.recycler_view_photos)).check(RecyclerViewItemCountAssertion(nbDays))
    }
}