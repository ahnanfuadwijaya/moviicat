package id.riverflows.moviicat.data.source.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import id.riverflows.moviicat.data.source.local.room.FavoriteDao
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.api.DetailApiService
import id.riverflows.moviicat.data.source.remote.response.MovieDetailResponse
import id.riverflows.moviicat.utils.UtilDataDummy
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class DetailRepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val apiService = Mockito.mock(DetailApiService::class.java)
    private val favoriteDao = Mockito.mock(FavoriteDao::class.java)
    private val repository = FakeDetailRepository(apiService, favoriteDao)
    private val movieId = 399566L
    private val tvId = 88396L
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Test
    fun getDetailMovie() {
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
    fun getDetailTv() {
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
    fun insertFavorite(){
        //TODO insert favorite test
    }

    @Test
    fun removeFavorite(){
        //TODO remove favorite test
    }

    @Test
    fun findFavoriteByIdAndType(){
        //TODO find favorite by id and type test
    }
}