package id.riverflows.moviicat.ui.home.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.TvListResponse
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.ui.home.HomeSharedViewModel
import id.riverflows.moviicat.util.UtilDataDummy
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {
    private lateinit var viewModel: HomeSharedViewModel
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var observer: Observer<Resource<TvListResponse>>
    @Mock
    private lateinit var repository: ListRepository
    private val testDispatcher = TestCoroutineDispatcher()
    private val dummyPage = 1
    private val dummyTotalPages = 1
    private val dummyTotalResults = 2
    private val dummyHttpErrorCode = 401

    @Before
    fun setup(){
        viewModel = HomeSharedViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getSuccessTvList() {
        val dummyList = UtilDataDummy.getTvList()
        val dummySuccessResponse = Resource.Success(TvListResponse(dummyPage, dummyList, dummyTotalPages, dummyTotalResults))
        viewModel.tvList.observeForever(observer)
        runBlocking {
            `when`(repository.getTvList()).thenReturn(dummySuccessResponse)
            viewModel.getTvList()
            delay(500)
            verify(observer).onChanged(dummySuccessResponse)
            val response = viewModel.tvList.value
            assertNotNull(response)
            assertTrue(response is Resource.Success)
            if(response is Resource.Success){
                assertThat(
                    response.value,
                    instanceOf(TvListResponse::class.java)
                )
                assertEquals(response.value.data, dummyList)
                assertEquals(response.value.data.size, dummyList.size)
            }
            viewModel.tvList.removeObserver(observer)
        }
    }

    @Test
    fun getNetworkErrorTvList(){
        val dummyNetworkErrorResponse = Resource.Failure(null)
        viewModel.tvList.observeForever(observer)
        runBlocking {
            `when`(repository.getTvList()).thenReturn(dummyNetworkErrorResponse)
            viewModel.getTvList()
            delay(500)
            verify(observer).onChanged(dummyNetworkErrorResponse)
            val response = viewModel.tvList.value
            assertNotNull(response)
            assertTrue(response is Resource.Failure)
            if(response is Resource.Failure) assertNull(response.code)
            viewModel.tvList.removeObserver(observer)
        }
    }

    @Test
    fun getHttpErrorTvList(){
        val dummyHttpErrorResponse = Resource.Failure(dummyHttpErrorCode)
        viewModel.tvList.observeForever(observer)
        runBlocking {
            `when`(repository.getTvList()).thenReturn(dummyHttpErrorResponse)
            viewModel.getTvList()
            delay(500)
            verify(observer).onChanged(dummyHttpErrorResponse)
            val response = viewModel.tvList.value
            assertNotNull(response)
            assertTrue(response is Resource.Failure)
            if(response is Resource.Failure){
                assertNotNull(response.code)
                assertTrue(response.code in 100..599)
            }
            viewModel.tvList.removeObserver(observer)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}