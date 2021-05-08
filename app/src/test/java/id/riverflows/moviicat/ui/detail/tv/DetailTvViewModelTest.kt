package id.riverflows.moviicat.ui.detail.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.TvDetailResponse
import id.riverflows.moviicat.data.source.repository.DetailRepository
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
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailTvViewModelTest{
    private val tvId = 88396L
    private val dummyHttpErrorCode = 401
    private lateinit var viewModel: DetailTvViewModel
    @Mock
    private lateinit var repository: DetailRepository
    @Mock
    private lateinit var observer: Observer<Resource<TvDetailResponse>>

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        viewModel = DetailTvViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getSuccessDetailTv() {
        val tvDetail = UtilDataDummy.getDetailTv(tvId) as TvDetailResponse
        val dummySuccessResponse: Resource<TvDetailResponse> = Resource.Success(tvDetail)
        viewModel.tv.observeForever(observer)
        runBlocking {
            `when`(repository.getDetailTv(tvId)).thenReturn(dummySuccessResponse)
            viewModel.getTv(tvId)
            delay(500)
            verify(observer).onChanged(dummySuccessResponse)
            val response = viewModel.tv.value as Resource.Success<TvDetailResponse>
            assertNotNull(response)
            Assert.assertThat(
                response.value,
                instanceOf(TvDetailResponse::class.java)
            )
            assertEquals(response.value, tvDetail)
            viewModel.tv.removeObserver(observer)
        }
    }

    @Test
    fun getNetworkErrorDetailTv(){
        val dummyNetworkErrorResponse: Resource<TvDetailResponse> = Resource.Failure(null)
        viewModel.tv.observeForever(observer)
        runBlocking {
            `when`(repository.getDetailTv(tvId)).thenReturn(dummyNetworkErrorResponse)
            viewModel.getTv(tvId)
            delay(500)
            verify(observer).onChanged(dummyNetworkErrorResponse)
            val response = viewModel.tv.value as Resource.Failure
            assertNotNull(response)
            Assert.assertThat(response, instanceOf(Resource.Failure::class.java))
            Assert.assertNull(response.code)
            viewModel.tv.removeObserver(observer)
        }
    }

    @Test
    fun getHttpErrorDetailTv(){
        val dummyHttpErrorResponse: Resource<TvDetailResponse> = Resource.Failure(dummyHttpErrorCode)
        viewModel.tv.observeForever(observer)
        runBlocking {
            `when`(repository.getDetailTv(tvId)).thenReturn(dummyHttpErrorResponse)
            viewModel.getTv(tvId)
            delay(500)
            verify(observer).onChanged(dummyHttpErrorResponse)
            val response = viewModel.tv.value as Resource.Failure
            assertNotNull(response)
            Assert.assertThat(response, instanceOf(Resource.Failure::class.java))
            assertNotNull(response.code)
            Assert.assertTrue(response.code in 100..599)
            viewModel.tv.removeObserver(observer)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}