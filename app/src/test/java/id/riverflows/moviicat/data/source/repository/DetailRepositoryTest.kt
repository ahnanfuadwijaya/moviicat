package id.riverflows.moviicat.data.source.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import id.riverflows.moviicat.data.source.local.room.FavoriteDao
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.api.DetailApiService
import id.riverflows.moviicat.data.source.remote.response.MovieDetailResponse
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
class DetailRepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val apiService = Mockito.mock(DetailApiService::class.java)
    private val favoriteDao = Mockito.mock(FavoriteDao::class.java)
    private val repository = FakeDetailRepository(apiService, favoriteDao)
    private val dummyErrorCode = 401
    private val movieId = 399566L
    private val tvId = 88396L
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Test
    fun getSuccessDetailMovie() {
        val dummyDetailMovieResponse = UtilDataDummy.getDetailMovie(movieId) as MovieDetailResponse
        runBlocking {
            doAnswer {
                dummyDetailMovieResponse
            }.`when`(apiService).getDetailMovie(movieId)
            val response = repository.getDetailMovie(movieId) as Resource.Success
            verify(apiService).getDetailMovie(movieId)
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Success::class.java))
            assertEquals(dummyDetailMovieResponse, response.value)
        }
    }

    @Test
    fun getNetworkErrorDetailMovie() {
        runBlocking {
            doAnswer {
                throw Exception()
            }.`when`(apiService).getDetailMovie(movieId)
            val response = repository.getDetailMovie(movieId) as Resource.Failure
            verify(apiService).getDetailMovie(movieId)
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Failure::class.java))
            assertEquals(null, response.code)
        }
    }

    @Test
    fun getHttpErrorDetailMovie() {
        val dummyResponseBody = "".toResponseBody()
        val dummyResponse = Response.error<MovieListResponse>(dummyErrorCode, dummyResponseBody)
        val httpException = HttpException(dummyResponse)
        runBlocking {
            doAnswer {
                throw httpException
            }.`when`(apiService).getDetailMovie(movieId)
            val response = repository.getDetailMovie(movieId) as Resource.Failure
            verify(apiService).getDetailMovie(movieId)
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Failure::class.java))
            assertNotNull(response.code)
            assertEquals(dummyErrorCode, response.code)
        }
    }

    @Test
    fun getSuccessDetailTv() {
        val dummyDetailTvResponse = UtilDataDummy.getDetailTv(tvId)
        runBlocking {
            doAnswer {
                dummyDetailTvResponse
            }.`when`(apiService).getDetailTv(tvId)
            val response = repository.getDetailTv(tvId) as Resource.Success
            verify(apiService).getDetailTv(tvId)
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Success::class.java))
            assertEquals(dummyDetailTvResponse, response.value)
        }
    }

    @Test
    fun getNetworkErrorDetailTv() {
        runBlocking {
            doAnswer {
                throw Exception()
            }.`when`(apiService).getDetailTv(tvId)
            val response = repository.getDetailTv(tvId) as Resource.Failure
            verify(apiService).getDetailTv(tvId)
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Failure::class.java))
            assertEquals(null, response.code)
        }
    }

    @Test
    fun getHttpErrorDetailTv() {
        val dummyResponseBody = "".toResponseBody()
        val dummyResponse = Response.error<TvListResponse>(dummyErrorCode, dummyResponseBody)
        val httpException = HttpException(dummyResponse)
        runBlocking {
            doAnswer {
                throw httpException
            }.`when`(apiService).getDetailTv(tvId)
            val response = repository.getDetailTv(tvId) as Resource.Failure
            verify(apiService).getDetailTv(tvId)
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Failure::class.java))
            assertNotNull(response.code)
            assertEquals(dummyErrorCode, response.code)
        }
    }
}