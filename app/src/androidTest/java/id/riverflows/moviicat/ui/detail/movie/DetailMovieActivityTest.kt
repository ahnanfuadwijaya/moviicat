package id.riverflows.moviicat.ui.detail.movie

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import id.riverflows.moviicat.R
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.util.UtilDataDummy
import id.riverflows.moviicat.util.UtilIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailMovieActivityTest{
    private lateinit var activityScenario: ActivityScenario<DetailMovieActivity>
    private val movieList = UtilDataDummy.getMovieList()

    @Before
    fun setup(){
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailMovieActivity::class.java)
        val movie = movieList[0]
        intent.putExtra(UtilConstants.EXTRA_MOVIE_ID, movie.id)
        activityScenario = ActivityScenario.launch(intent)
        IdlingRegistry.getInstance().register(UtilIdlingResource.getEspressoIdlingResourceForMainActivity())
    }

    @Test
    fun loadView(){
        onView(ViewMatchers.withId(R.id.iv_poster)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.tv_title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.rating_separator)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.tv_popularity)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.tv_rate)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.tv_release_date)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.date_separator)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.tv_release_status)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(android.R.id.content)).perform(ViewActions.swipeUp())
        onView(ViewMatchers.withId(R.id.container_chips)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.chip_genres)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.field_overview)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.tv_value_overview)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @After
    fun tearDown() {
        activityScenario.close()
        IdlingRegistry.getInstance().unregister(UtilIdlingResource.getEspressoIdlingResourceForMainActivity())
    }
}