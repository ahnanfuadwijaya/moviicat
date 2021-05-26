package id.riverflows.moviicat.ui.home

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.riverflows.moviicat.R
import id.riverflows.moviicat.util.UtilIdlingResource
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchFragmentTest{
    private lateinit var activityScenario: ActivityScenario<HomeActivity>
    private val dummySearchText = "search"

    @Before
    fun setup(){
        activityScenario = ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(UtilIdlingResource.idlingResource)
    }

    @Test
    fun loadSearch(){
        onView(withId(android.R.id.content)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
    }

    @Test
    fun loadSearchMovie(){
        onView(withId(R.id.menu_search)).perform(click())
        onView(withId(R.id.search_view)).perform(click())
        onView(withId(R.id.search_view)).perform(typeSearchViewText())
        onView(withId(R.id.search_view)).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.rv_grid_or_list)).check(matches(isDisplayed()))
        onView(withId(android.R.id.content)).perform(swipeUp())
        onView(withId(android.R.id.content)).perform(swipeLeft())
        onView(withId(android.R.id.content)).perform(swipeDown())
        onView(withId(android.R.id.content)).perform(swipeRight())
    }

    @Test
    fun loadSearchTv(){
        onView(withId(R.id.menu_search)).perform(click())
        onView(withId(android.R.id.content)).perform(swipeLeft())
        onView(withId(R.id.search_view)).perform(click())
        onView(withId(R.id.search_view)).perform(typeSearchViewText())
        onView(withId(R.id.search_view)).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.rv_grid_or_list)).check(matches(isDisplayed()))
        onView(withId(android.R.id.content)).perform(swipeUp())
        onView(withId(android.R.id.content)).perform(swipeLeft())
        onView(withId(android.R.id.content)).perform(swipeDown())
        onView(withId(android.R.id.content)).perform(swipeRight())
    }

    @After
    fun tearDown() {
        activityScenario.close()
        IdlingRegistry.getInstance().unregister(UtilIdlingResource.idlingResource)
    }

    private fun typeSearchViewText(): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Change view text"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as SearchView).setQuery(dummySearchText, true)
            }
        }
    }
}