package id.riverflows.moviicat.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.MovieDetailResponse
import id.riverflows.moviicat.data.source.repository.DetailRepository
import id.riverflows.moviicat.util.DataDummy
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class DetailMovieViewModelTest{
    private val movieId = 399566L
    private lateinit var viewModel: DetailMovieViewModel
    private val repository = mock(DetailRepository::class.java)

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        viewModel = DetailMovieViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun getMovie() {
        val movie = DataDummy.getMovie(movieId)
        val observer = mock(Observer::class.java) as Observer<Resource<MovieDetailResponse>>
        viewModel.movie.observeForever(observer)
        runBlocking {
            viewModel.getMovie(movieId)
            delay(500)
            //verify(observer).onChanged(movie)
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