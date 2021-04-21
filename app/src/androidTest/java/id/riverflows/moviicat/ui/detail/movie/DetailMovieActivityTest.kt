package id.riverflows.moviicat.ui.detail.movie

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import id.riverflows.moviicat.R
import id.riverflows.moviicat.util.DataDummy
import id.riverflows.moviicat.util.UtilConstants
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailMovieActivityTest{
    private lateinit var activityScenario: ActivityScenario<DetailMovieActivity>
    private val movieList = DataDummy.getMovieList()

    @Before
    fun setup(){
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailMovieActivity::class.java)
        val movie = movieList[0]
        intent.putExtra(UtilConstants.EXTRA_MOVIE_ID, movie.id)
        activityScenario = ActivityScenario.launch(intent)
    }

    @Test
    fun loadView(){
        onView(ViewMatchers.withId(R.id.iv_poster)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }
}