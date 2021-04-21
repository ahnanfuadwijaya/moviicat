package id.riverflows.moviicat.ui.detail.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.data.entity.TvDetailEntity
import id.riverflows.moviicat.ui.detail.movie.DetailMovieViewModel
import id.riverflows.moviicat.util.DataDummy
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class DetailTvViewModelTest{
    private val tvId = 88396
    lateinit var viewModel: DetailTvViewModel
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup(){
        viewModel = DetailTvViewModel()
        Dispatchers.setMain(testDispatcher)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun getMovie() {
        val tv = DataDummy.getTv(tvId)
        val observer = Mockito.mock(Observer::class.java) as Observer<TvDetailEntity>
        viewModel.tv.observeForever(observer)
        runBlocking {
            viewModel.getTv(tvId)
        }
        Mockito.verify(observer).onChanged(tv)
        assertNotNull(viewModel.tv.value)
        assertEquals(viewModel.tv.value, tv)
        viewModel.tv.removeObserver(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}