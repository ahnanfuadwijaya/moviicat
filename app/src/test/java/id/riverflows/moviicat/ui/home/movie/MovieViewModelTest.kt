package id.riverflows.moviicat.ui.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.util.DataDummy
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import id.riverflows.moviicat.utils.getValueForTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class MovieViewModelTest {
    lateinit var viewModel: MovieViewModel
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup(){
        viewModel = MovieViewModel()
        Dispatchers.setMain(testDispatcher)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun getMovieList() {
        val list = DataDummy.getMovieList()
        val observer = mock(Observer::class.java) as Observer<List<MovieDetailEntity>>
        viewModel.movieList.observeForever(observer)
        runBlocking {
            viewModel.getMovieList()
        }
        verify(observer).onChanged(list)
        assertNotNull(viewModel.movieList.getValueForTest())
        assertEquals(viewModel.movieList.getValueForTest(), list)
        assertEquals(viewModel.movieList.getValueForTest()?.size, list.size)
        viewModel.movieList.removeObserver(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}