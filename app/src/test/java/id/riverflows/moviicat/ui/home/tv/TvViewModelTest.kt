package id.riverflows.moviicat.ui.home.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.TvDetailResponse
import id.riverflows.moviicat.data.source.remote.response.TvListResponse
import id.riverflows.moviicat.data.source.repository.ListRepository
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
class TvViewModelTest {
    private lateinit var viewModel: TvViewModel
    private val repository = mock(ListRepository::class.java)
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup(){
        viewModel = TvViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun getTvList() {
        val list = DataDummy.getTvList() as Resource<TvListResponse>
        val observer = mock(Observer::class.java) as Observer<Resource<TvListResponse>>
        viewModel.tvList.observeForever(observer)
        runBlocking {
            viewModel.getTvList()
            delay(500)
            verify(observer).onChanged(list)
            assertNotNull(viewModel.tvList.value)
            assertEquals(viewModel.tvList.value, list)
            viewModel.tvList.removeObserver(observer)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}