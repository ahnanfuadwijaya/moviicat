package id.riverflows.moviicat.ui.detail.tv

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import id.riverflows.moviicat.R
import id.riverflows.moviicat.ui.detail.movie.DetailMovieActivity
import id.riverflows.moviicat.util.DataDummy
import id.riverflows.moviicat.util.UtilConstants
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DetailTvActivityTest{
    private lateinit var activityScenario: ActivityScenario<DetailTvActivity>
    private val tvList = DataDummy.getTvList()

    @Before
    fun setup(){
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailTvActivity::class.java)
        val tv = tvList[0]
        intent.putExtra(UtilConstants.EXTRA_TV_ID, tv.id)
        activityScenario = ActivityScenario.launch(intent)
    }

    @Test
    fun loadView(){
        Espresso.onView(ViewMatchers.withId(R.id.iv_poster)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }
}