package id.riverflows.moviicat.data.source.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.api.ListApiService
import id.riverflows.moviicat.data.source.remote.response.MovieListResponse
import id.riverflows.moviicat.data.source.remote.response.TvListResponse
import id.riverflows.moviicat.util.UtilDataDummy
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class ListRepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val apiService = Mockito.mock(ListApiService::class.java)
    private val repository = FakeListRepository(apiService)
    private val dummyPage = 1
    private val dummyTotalPages = 1
    private val dummyTotalResults = 2
    private val dummyErrorCode = 401
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Test
    fun getSuccessMovieList() {
        val dummyMovieList = UtilDataDummy.getMovieList()
        val dummyResponse = MovieListResponse(dummyPage, dummyMovieList, dummyTotalPages, dummyTotalResults)
        runBlocking {
            doAnswer {
                dummyResponse
            }.`when`(apiService).getMovieList()
            val response = repository.getMovieList() as Resource.Success
            verify(apiService).getMovieList()
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Success::class.java))
            assertEquals(dummyPage, response.value.page)
            assertEquals(dummyTotalPages, response.value.totalPages)
            assertEquals(dummyTotalResults, response.value.totalResults)
            assertEquals(dummyMovieList, response.value.data)
        }
    }

    @Test
    fun getNetworkErrorMovieList() {
        runBlocking {
            doAnswer {
                throw Exception()
            }.`when`(apiService).getMovieList()
            val response = repository.getMovieList() as Resource.Failure
            verify(apiService).getMovieList()
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Failure::class.java))
            assertEquals(null, response.code)
        }
    }

    @Test
    fun getHttpErrorMovieList() {
        val dummyResponseBody = "".toResponseBody()
        val dummyResponse = Response.error<MovieListResponse>(dummyErrorCode, dummyResponseBody)
        val httpException = HttpException(dummyResponse)
        runBlocking {
            doAnswer {
                throw httpException
            }.`when`(apiService).getMovieList()
            val response = repository.getMovieList() as Resource.Failure
            verify(apiService).getMovieList()
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Failure::class.java))
            assertNotNull(response.code)
            assertEquals(dummyErrorCode, response.code)
        }
    }

    @Test
    fun getSuccessTvList() {
        val dummyTvList = UtilDataDummy.getTvList()
        val dummyResponse = TvListResponse(dummyPage, dummyTvList, dummyTotalPages, dummyTotalResults)
        runBlocking {
            doAnswer {
                dummyResponse
            }.`when`(apiService).getTvList()
            val response = repository.getTvList() as Resource.Success
            verify(apiService).getTvList()
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Success::class.java))
            assertEquals(dummyPage, response.value.page)
            assertEquals(dummyTotalPages, response.value.totalPages)
            assertEquals(dummyTotalResults, response.value.totalResults)
            assertEquals(dummyTvList, response.value.data)
        }
    }

    @Test
    fun getNetworkErrorTvList() {
        runBlocking {
            doAnswer {
                throw Exception()
            }.`when`(apiService).getTvList()
            val response = repository.getTvList() as Resource.Failure
            verify(apiService).getTvList()
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Failure::class.java))
            assertEquals(null, response.code)
        }
    }

    @Test
    fun getHttpErrorTvList() {
        val dummyResponseBody = "".toResponseBody()
        val dummyResponse = Response.error<TvListResponse>(dummyErrorCode, dummyResponseBody)
        val httpException = HttpException(dummyResponse)
        runBlocking {
            doAnswer {
                throw httpException
            }.`when`(apiService).getTvList()
            val response = repository.getTvList() as Resource.Failure
            verify(apiService).getTvList()
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Failure::class.java))
            assertNotNull(response.code)
            assertEquals(dummyErrorCode, response.code)
        }
    }
}