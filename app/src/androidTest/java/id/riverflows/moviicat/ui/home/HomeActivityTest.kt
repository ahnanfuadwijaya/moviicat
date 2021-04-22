package id.riverflows.moviicat.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import id.riverflows.moviicat.R
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeActivityTest{
    private lateinit var activityScenario: ActivityScenario<HomeActivity>

    @Before
    fun setup(){
        activityScenario = ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun loadHome(){
        onView(withId(android.R.id.content)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(android.R.id.content)).perform(swipeUp())
        onView(withId(android.R.id.content)).perform(swipeLeft())
        onView(withId(android.R.id.content)).perform(swipeDown())
        onView(withId(android.R.id.content)).perform(swipeRight())
    }

    @Test
    fun loadMovie() {
        onView(withId(android.R.id.content)).perform(swipeUp())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShow(){
        onView(withId(android.R.id.content)).perform(swipeLeft())
        onView(withId(android.R.id.content)).perform(swipeUp())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }
}