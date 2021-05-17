package id.riverflows.moviicat.data.source.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.Pager
import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import id.riverflows.moviicat.data.entity.MovieEntity
import id.riverflows.moviicat.data.entity.TvEntity
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import id.riverflows.moviicat.utils.UtilDataDummy
import id.riverflows.moviicat.utils.UtilPagingData.collectDataForTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListRepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()
    @Test
    fun getMoviePaged() {
        val dummyMovieList = UtilDataDummy.getMovieList()
        val pager: Pager<Long, MovieEntity> = mock()
        runBlocking {
            whenever(pager.flow).thenReturn(flowOf(PagingData.from(dummyMovieList)))
            val data = pager.flow
            assertNotNull(data)
            data.collect {
                val list = it.collectDataForTest()
                assertNotNull(list)
                assertEquals(dummyMovieList.size, list.size)
                assertEquals(dummyMovieList, list)
            }
        }
    }

    @Test
    fun getTvPaged() {
        val dummyTvList = UtilDataDummy.getTvList()
        val pager: Pager<Long, TvEntity> = mock()
        runBlocking {
            whenever(pager.flow).thenReturn(flowOf(PagingData.from(dummyTvList)))
            val data = pager.flow
            assertNotNull(data)
            data.collect {
                val list = it.collectDataForTest()
                assertNotNull(list)
                assertEquals(dummyTvList.size, list.size)
                assertEquals(dummyTvList, list)
            }
        }
    }

    @Test
    fun getFavoritePaged(){
        val dummyFavoriteList = UtilDataDummy.getFavoriteList()
        val pager: Pager<Int, FavoriteEntity> = mock()
        runBlocking {
            whenever(pager.flow).thenReturn(flowOf(PagingData.from(dummyFavoriteList)))
            val data = pager.flow
            assertNotNull(data)
            val liveData = MutableLiveData<PagingData<FavoriteEntity>>()
            val observer: Observer<PagingData<FavoriteEntity>> = mock()
            liveData.observeForever(observer)
            data.collect {
                liveData.value = it
                val list = it.collectDataForTest()
                verify(observer).onChanged(it)
                assertNotNull(list)
                assertEquals(dummyFavoriteList.size, list.size)
                assertEquals(dummyFavoriteList, list)
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun getMovieSearchResultPaged(){
        val dummyMovieSearchResult = UtilDataDummy.getMovieList()
        val pager: Pager<Long, MovieEntity> = mock()
        runBlocking {
            whenever(pager.flow).thenReturn(flowOf(PagingData.from(dummyMovieSearchResult)))
            val data = pager.flow
            assertNotNull(data)
            data.collect {
                val list = it.collectDataForTest()
                assertNotNull(list)
                assertEquals(dummyMovieSearchResult.size, list.size)
                assertEquals(dummyMovieSearchResult, list)
            }
        }
    }

    @Test
    fun getTvSearchResultPaged(){
        val dummyTvSearchResult = UtilDataDummy.getTvList()
        val pager: Pager<Long, TvEntity> = mock()
        runBlocking {
            whenever(pager.flow).thenReturn(flowOf(PagingData.from(dummyTvSearchResult)))
            val data = pager.flow
            assertNotNull(data)
            data.collect {
                val list = it.collectDataForTest()
                assertNotNull(list)
                assertEquals(dummyTvSearchResult.size, list.size)
                assertEquals(dummyTvSearchResult, list)
            }
        }
    }
}