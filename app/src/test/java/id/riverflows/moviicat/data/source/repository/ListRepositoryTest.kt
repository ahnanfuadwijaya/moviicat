package id.riverflows.moviicat.data.source.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import id.riverflows.moviicat.data.source.local.room.FavoriteDao
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.api.ListApiService
import id.riverflows.moviicat.data.source.remote.response.MovieListResponse
import id.riverflows.moviicat.data.source.remote.response.TvListResponse
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import id.riverflows.moviicat.utils.UtilDataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ListRepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val apiService = Mockito.mock(ListApiService::class.java)
    private val favoriteDao = Mockito.mock(FavoriteDao::class.java)
    private val dummyPage = 1
    private val dummyTotalPages = 1
    private val dummyTotalResults = 2
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Test
    fun getMoviePaged() {
        val dummyMovieList = UtilDataDummy.getMovieList()
        val dummyResponse = MovieListResponse(dummyPage, dummyMovieList, dummyTotalPages, dummyTotalResults)
        runBlocking {
            doAnswer {
                dummyResponse
            }.`when`(apiService).getMoviePaged(1)
            val response = apiService.getMoviePaged(1)
            verify(apiService).getMoviePaged(1)
            assertNotNull(response)
            assertEquals(dummyPage, response.page)
            assertEquals(dummyTotalPages, response.totalPages)
            assertEquals(dummyTotalResults, response.totalResults)
            assertEquals(dummyMovieList, response.data)
        }
    }

    @Test
    fun getTvPaged() {
        val dummyTvList = UtilDataDummy.getTvList()
        val dummyResponse = TvListResponse(dummyPage, dummyTvList, dummyTotalPages, dummyTotalResults)
        runBlocking {
            doAnswer {
                dummyResponse
            }.`when`(apiService).getTvPaged(1)
            val response = apiService.getTvPaged(1)
            verify(apiService).getTvPaged(1)
            assertNotNull(response)
            assertThat(response, IsInstanceOf.instanceOf(Resource.Success::class.java))
            assertEquals(dummyPage, response.page)
            assertEquals(dummyTotalPages, response.totalPages)
            assertEquals(dummyTotalResults, response.totalResults)
            assertEquals(dummyTvList, response.data)
        }
    }

    @Test
    fun getFavoritePaged(){
        //TODO get favorite paged test
    }

    @Test
    fun getMovieSearchResultPaged(){
        //TODO get movie search result paged test
    }

    @Test
    fun getTvSearchResultPaged(){
        //TODO get tv search result paged test
    }
}