package id.riverflows.moviicat.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.MovieDetailResponse
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
class DetailMovieViewModelTest{
    private val movieId = 399566L
    private val dummyHttpErrorCode = 401
    private lateinit var viewModel: DetailMovieViewModel
    @Mock
    private lateinit var repository: DetailRepository
    @Mock
    private lateinit var observer: Observer<Resource<MovieDetailResponse>>

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

    @Test
    fun getSuccessDetailMovie() {
        val movieDetail = UtilDataDummy.getDetailMovie(movieId) as MovieDetailResponse
        val dummySuccessResponse: Resource<MovieDetailResponse> = Resource.Success(movieDetail)
        viewModel.movie.observeForever(observer)
        runBlocking {
            `when`(repository.getDetailMovie(movieId)).thenReturn(dummySuccessResponse)
            viewModel.getMovie(movieId)
            delay(500)
            verify(observer).onChanged(dummySuccessResponse)
            val response = viewModel.movie.value
            assertNotNull(response)
            assertTrue(response is Resource.Success)
            if(response is Resource.Success){
                assertThat(response.value, instanceOf(MovieDetailResponse::class.java))
                assertEquals(response.value, movieDetail)
            }
            viewModel.movie.removeObserver(observer)
        }
    }

    @Test
    fun getNetworkErrorDetailMovie(){
        val dummyNetworkErrorResponse: Resource<MovieDetailResponse> = Resource.Failure(null)
        viewModel.movie.observeForever(observer)
        runBlocking {
            `when`(repository.getDetailMovie(movieId)).thenReturn(dummyNetworkErrorResponse)
            viewModel.getMovie(movieId)
            delay(500)
            verify(observer).onChanged(dummyNetworkErrorResponse)
            val response = viewModel.movie.value
            assertNotNull(response)
            assertTrue(response is Resource.Failure)
            if(response is Resource.Failure) assertNull(response.code)
            viewModel.movie.removeObserver(observer)
        }
    }

    @Test
    fun getHttpErrorDetailMovie(){
        val dummyHttpErrorResponse: Resource<MovieDetailResponse> = Resource.Failure(dummyHttpErrorCode)
        viewModel.movie.observeForever(observer)
        runBlocking {
            `when`(repository.getDetailMovie(movieId)).thenReturn(dummyHttpErrorResponse)
            viewModel.getMovie(movieId)
            delay(500)
            verify(observer).onChanged(dummyHttpErrorResponse)
            val response = viewModel.movie.value
            assertNotNull(response)
            assertTrue(response is Resource.Failure)
            if(response is Resource.Failure){
                assertNotNull(response.code)
                assertTrue(response.code in 100..599)
            }
            viewModel.movie.removeObserver(observer)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}