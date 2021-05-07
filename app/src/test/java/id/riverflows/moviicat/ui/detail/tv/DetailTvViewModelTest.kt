package id.riverflows.moviicat.ui.detail.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.riverflows.moviicat.data.source.remote.response.TvDetailResponse
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
class DetailTvViewModelTest{
    private val tvId = 88396
    private lateinit var viewModel: DetailTvViewModel
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

    @Test
    @Suppress("UNCHECKED_CAST")
    fun getDetailTv() {
        val tv = DataDummy.getTv(tvId)
        val observer = mock(Observer::class.java) as Observer<TvDetailResponse>
        viewModel.tv.observeForever(observer)
        runBlocking {
            viewModel.getTv(tvId)
            delay(500)
            verify(observer).onChanged(tv)
            assertNotNull(viewModel.tv.value)
            assertEquals(viewModel.tv.value, tv)
            viewModel.tv.removeObserver(observer)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}