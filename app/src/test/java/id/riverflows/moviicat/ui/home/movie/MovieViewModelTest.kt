package id.riverflows.moviicat.ui.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.MovieListResponse
import id.riverflows.moviicat.data.source.repository.ListRepository
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
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var observer: Observer<Resource<MovieListResponse>>
    @Mock
    private lateinit var repository: ListRepository
    private val testDispatcher = TestCoroutineDispatcher()
    private val dummyPage = 1
    private val dummyTotalPages = 1
    private val dummyTotalResults = 2
    private val dummyHttpErrorCode = 401

    @Before
    fun setup(){
        viewModel = MovieViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getSuccessMovieList() {
        val dummyList = UtilDataDummy.getMovieList()
        val dummySuccessResponse = Resource.Success(MovieListResponse(dummyPage, dummyList, dummyTotalPages, dummyTotalResults))
        viewModel.movieList.observeForever(observer)
        runBlocking {
            `when`(repository.getMovieList()).thenReturn(dummySuccessResponse)
            viewModel.getMovieList()
            delay(500)
            verify(observer).onChanged(dummySuccessResponse)
            val response = viewModel.movieList.value
            assertNotNull(response)
            assertTrue(response is Resource.Success)
            if(response is Resource.Success){
                assertThat(response.value, instanceOf(MovieListResponse::class.java))
                assertEquals(response.value.data, dummyList)
                assertEquals(response.value.data.size, dummyList.size)
            }
            viewModel.movieList.removeObserver(observer)
        }
    }

    @Test
    fun getNetworkErrorMovieList(){
        val dummyNetworkErrorResponse = Resource.Failure(null)
        viewModel.movieList.observeForever(observer)
        runBlocking {
            `when`(repository.getMovieList()).thenReturn(dummyNetworkErrorResponse)
            viewModel.getMovieList()
            delay(500)
            verify(observer).onChanged(dummyNetworkErrorResponse)
            val response = viewModel.movieList.value
            assertNotNull(response)
            assertTrue(response is Resource.Failure)
            if(response is Resource.Failure) assertNull(response.code)
            viewModel.movieList.removeObserver(observer)
        }
    }

    @Test
    fun getHttpErrorMovieList(){
        val dummyHttpErrorResponse = Resource.Failure(dummyHttpErrorCode)
        viewModel.movieList.observeForever(observer)
        runBlocking {
            `when`(repository.getMovieList()).thenReturn(dummyHttpErrorResponse)
            viewModel.getMovieList()
            delay(500)
            verify(observer).onChanged(dummyHttpErrorResponse)
            val response = viewModel.movieList.value
            assertNotNull(response)
            assertTrue(response is Resource.Failure)
            if(response is Resource.Failure){
                assertNotNull(response.code)
                assertTrue(response.code in 100..599)
            }
            viewModel.movieList.removeObserver(observer)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}