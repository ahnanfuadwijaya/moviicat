package id.riverflows.moviicat.ui.detail.tv

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import id.riverflows.moviicat.R
import id.riverflows.moviicat.ui.util.UtilDataDummy
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.util.UtilIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test


class DetailTvActivityTest{
    private lateinit var activityScenario: ActivityScenario<DetailTvActivity>
    private val tvList = UtilDataDummy.getTvList()

    @Before
    fun setup(){
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailTvActivity::class.java)
        val tv = tvList[0]
        intent.putExtra(UtilConstants.EXTRA_TV_ID, tv.id)
        activityScenario = ActivityScenario.launch(intent)
        IdlingRegistry.getInstance().register(UtilIdlingResource.idlingResource)
    }

    @Test
    fun loadView(){
        onView(withId(R.id.iv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_popularity)).check(matches(isDisplayed()))
        onView(withId(R.id.rating_separator)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rate)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date)).check(matches(isDisplayed()))
        onView(withId(R.id.date_separator)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))
        onView(withId(android.R.id.content)).perform(ViewActions.swipeUp())
        onView(withId(R.id.container_chips)).check(matches(isDisplayed()))
        onView(withId(R.id.chip_genres)).check(matches(isDisplayed()))
        onView(withId(R.id.field_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_value_overview)).check(matches(isDisplayed()))
        onView(withId(android.R.id.content)).perform(ViewActions.swipeUp())
    }

    @After
    fun tearDown() {
        activityScenario.close()
        IdlingRegistry.getInstance().unregister(UtilIdlingResource.idlingResource)
    }
}