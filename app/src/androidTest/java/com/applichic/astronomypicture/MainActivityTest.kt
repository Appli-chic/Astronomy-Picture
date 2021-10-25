package com.applichic.astronomypicture

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    var rule = activityScenarioRule<MainActivity>()

    @Test
    fun checkBottomNavigationIsDisplayed() {
        val scenario = rule.scenario
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()))
    }
}