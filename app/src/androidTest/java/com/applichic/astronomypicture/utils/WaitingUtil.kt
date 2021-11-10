package com.applichic.astronomypicture.utils

import android.view.View

import androidx.test.espresso.matcher.ViewMatchers.isDisplayed

import androidx.test.espresso.Espresso.onView

import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Matcher
import androidx.test.espresso.*
import java.lang.Exception


class ViewShownIdlingResource(private val viewMatcher: Matcher<View>) :
    IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null
    override fun isIdleNow(): Boolean {
        val view = getView(viewMatcher)
        val idle = view == null || view.isShown
        if (idle && resourceCallback != null) {
            resourceCallback!!.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

    override fun getName(): String {
        return this.toString() + viewMatcher.toString()
    }

    companion object {
        private fun getView(viewMatcher: Matcher<View>): View? {
            return try {
                val viewInteraction = onView(viewMatcher)
                val finderField = viewInteraction.javaClass.getDeclaredField("viewFinder")
                finderField.isAccessible = true
                val finder = finderField[viewInteraction] as ViewFinder
                finder.view
            } catch (e: Exception) {
                null
            }
        }
    }
}


