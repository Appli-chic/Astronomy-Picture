package com.applichic.astronomypicture.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

import androidx.test.espresso.matcher.BoundedMatcher

import org.hamcrest.Description
import org.hamcrest.Matcher
import androidx.test.espresso.NoMatchingViewException

import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.BaseMatcher


fun <T> first(matcher: Matcher<T>): Matcher<T> {
    return object : BaseMatcher<T>() {
        var isFirst = true
        override fun matches(item: Any): Boolean {
            if (isFirst && matcher.matches(item)) {
                isFirst = false
                return true
            }
            return false
        }

        override fun describeTo(description: Description) {
            description.appendText("should return first matching item")
        }
    }
}

class RecyclerViewItemCountAssertion(private val expectedCount: Int) :
    ViewAssertion {
    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter!!.itemCount, `is`(expectedCount))
    }
}

fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                ?: // has no item on such position
                return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}