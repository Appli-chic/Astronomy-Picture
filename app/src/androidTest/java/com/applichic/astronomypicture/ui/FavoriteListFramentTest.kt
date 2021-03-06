package com.applichic.astronomypicture.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.applichic.astronomypicture.MainActivity
import com.applichic.astronomypicture.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteListFramentTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Before
    fun jumpToThePage() {
        Espresso.onView(ViewMatchers.withId(R.id.main_favorite_list))
            .perform(ViewActions.click())
    }

    @Test
    fun onDisplayingFavorites() {
        // Check if the list of entries is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_favorites))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}