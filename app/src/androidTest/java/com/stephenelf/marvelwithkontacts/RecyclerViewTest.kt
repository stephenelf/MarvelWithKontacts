package com.stephenelf.marvelwithkontacts

import android.test.suitebuilder.annotation.LargeTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule

@RunWith(AndroidJUnit4::class)
@LargeTest

class RecyclerViewTest{

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Test
    fun checkRecyclerView(){
        onView(withId(R.id.people_view)).perform(click())               // click() is a ViewAction
            .check(matches(isDisplayed()))
    }
}