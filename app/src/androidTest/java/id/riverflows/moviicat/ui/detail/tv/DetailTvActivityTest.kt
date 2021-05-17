package id.riverflows.moviicat.ui.detail.tv

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import id.riverflows.moviicat.R
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.utils.UtilDataDummy
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
        IdlingRegistry.getInstance().register(UtilIdlingResource.getEspressoIdlingResourceForMainActivity())
    }

    @Test
    fun loadView(){
        Espresso.onView(ViewMatchers.withId(R.id.iv_poster)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_popularity)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rating_separator)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_rate)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_date)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.date_separator)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_status)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(android.R.id.content)).perform(ViewActions.swipeUp())
        Espresso.onView(ViewMatchers.withId(R.id.container_chips)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.chip_genres)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.field_overview)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_value_overview)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(android.R.id.content)).perform(ViewActions.swipeUp())
    }

    @After
    fun tearDown() {
        activityScenario.close()
        IdlingRegistry.getInstance().unregister(UtilIdlingResource.getEspressoIdlingResourceForMainActivity())
    }
}