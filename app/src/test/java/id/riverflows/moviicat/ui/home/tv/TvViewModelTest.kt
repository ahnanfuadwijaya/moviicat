package id.riverflows.moviicat.ui.home.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.riverflows.moviicat.data.entity.TvDetailEntity
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
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class TvViewModelTest {
    lateinit var viewModel: TvViewModel
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup(){
        viewModel = TvViewModel()
        Dispatchers.setMain(testDispatcher)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun getTvList() {
        val list = DataDummy.getTvList()
        val observer = Mockito.mock(Observer::class.java) as Observer<List<TvDetailEntity>>
        viewModel.tvList.observeForever(observer)
        runBlocking {
            viewModel.getTvList()
        }
        Mockito.verify(observer).onChanged(list)
        assertNotNull(viewModel.tvList.getValueForTest())
        assertEquals(viewModel.tvList.getValueForTest(), list)
        assertEquals(viewModel.tvList.getValueForTest()?.size, list.size)
        viewModel.tvList.removeObserver(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}