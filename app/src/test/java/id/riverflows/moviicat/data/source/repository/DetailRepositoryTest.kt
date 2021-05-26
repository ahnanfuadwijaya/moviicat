package id.riverflows.moviicat.data.source.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import id.riverflows.moviicat.data.source.local.room.FavoriteDao
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.data.source.remote.api.DetailApiService
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import id.riverflows.moviicat.utils.UtilDataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class DetailRepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val apiService = Mockito.mock(DetailApiService::class.java)
    private val favoriteDao = Mockito.mock(FavoriteDao::class.java)
    private val movieId = 399566L
    private val tvId = 88396L
    private val dummyInsertResult = movieId
    private val dummyRemoveResult = 1
    private val dummyType = UtilConstants.TYPE_MOVIE
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Test
    fun getDetailMovie() {
        val dummyDetailMovieResponse = UtilDataDummy.getDetailMovie(movieId)
        runBlocking {
            doAnswer {
                dummyDetailMovieResponse
            }.`when`(apiService).getDetailMovie(movieId)
            val response = apiService.getDetailMovie(movieId)
            verify(apiService).getDetailMovie(movieId)
            assertNotNull(response)
            assertEquals(dummyDetailMovieResponse, response)
        }
    }

    @Test
    fun getDetailTv() {
        val dummyDetailTvResponse = UtilDataDummy.getDetailTv(tvId)
        runBlocking {
            doAnswer {
                dummyDetailTvResponse
            }.`when`(apiService).getDetailTv(tvId)
            val response = apiService.getDetailTv(tvId)
            verify(apiService).getDetailTv(tvId)
            assertNotNull(response)
            assertEquals(dummyDetailTvResponse, response)
        }
    }

    @Test
    fun insertFavorite(){
        runBlocking {
            val dummyFavorite = UtilDataDummy.getFavoriteByIdAndType(movieId, dummyType) as FavoriteEntity
            Mockito.`when`(favoriteDao.insert(dummyFavorite)).thenReturn(dummyInsertResult)
            val response = favoriteDao.insert(dummyFavorite)
            assertNotNull(response)
            assertEquals(response, dummyInsertResult)
        }
    }

    @Test
    fun removeFavorite(){
        runBlocking {
            val dummyFavorite = UtilDataDummy.getFavoriteByIdAndType(movieId, dummyType) as FavoriteEntity
            Mockito.`when`(favoriteDao.remove(dummyFavorite)).thenReturn(dummyRemoveResult)
            val response = favoriteDao.remove(dummyFavorite)
            assertNotNull(response)
            assertEquals(response, dummyRemoveResult)
        }
    }

    @Test
    fun findFavoriteByIdAndType(){
        runBlocking {
            val dummyFavorite = UtilDataDummy.getFavoriteByIdAndType(movieId, dummyType)
            Mockito.`when`(favoriteDao.findByIdAndType(movieId, dummyType)).thenReturn(dummyFavorite)
            val response = favoriteDao.findByIdAndType(movieId, dummyType)
            assertNotNull(response)
            assertEquals(response, dummyFavorite)
        }
    }
}