package id.riverflows.moviicat.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.ui.home.movie.MovieViewModel
import id.riverflows.moviicat.util.DataDummy
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import id.riverflows.moviicat.utils.getValueForTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
class DetailMovieViewModelTest {
    private val movieId = 399566
    lateinit var viewModel: DetailMovieViewModel
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup(){
        viewModel = DetailMovieViewModel()
        Dispatchers.setMain(testDispatcher)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun getMovie() {
        val movie = DataDummy.getMovie(movieId)
        val observer = mock(Observer::class.java) as Observer<MovieDetailEntity>
        viewModel.movie.observeForever(observer)
        runBlocking {
            viewModel.getMovie(movieId)
            delay(500)
            verify(observer).onChanged(movie)
            assertNotNull(viewModel.movie.value)
            assertEquals(viewModel.movie.value, movie)
            viewModel.movie.removeObserver(observer)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}