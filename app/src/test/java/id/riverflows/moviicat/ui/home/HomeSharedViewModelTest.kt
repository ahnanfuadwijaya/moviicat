package id.riverflows.moviicat.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import id.riverflows.moviicat.utils.UtilDataDummy
import id.riverflows.moviicat.utils.UtilPagingData.collectDataForTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeSharedViewModelTest{
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var favoriteObserver: Observer<PagingData<FavoriteEntity>>
    @Mock
    private lateinit var repository: ListRepository
    private val testDispatcher = TestCoroutineDispatcher()
    private val dummyQuery = "dummy query"

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getFavoritePaged(){
        val dummyList = UtilDataDummy.getFavoriteList()
        val mutableLiveData = MutableLiveData<PagingData<FavoriteEntity>>()
        mutableLiveData.observeForever(favoriteObserver)
        runBlocking {
            delay(500)
            mutableLiveData.value = PagingData.from(dummyList)
            whenever(repository.getFavoritePaged()).thenReturn(mutableLiveData)
            val pagingLiveData = repository.getFavoritePaged()
            val list = pagingLiveData.value?.collectDataForTest()
            verify(favoriteObserver).onChanged(mutableLiveData.value)
            assertNotNull(list)
            assertEquals(dummyList, list)
            assertEquals(dummyList.size, list.size)
        }
        mutableLiveData.removeObserver(favoriteObserver)
    }

    @Test
    fun moviePaged(){
        val dummyList = UtilDataDummy.getMovieList()
        runBlocking {
            whenever(repository.getMoviePaged()).thenReturn(flowOf(PagingData.from(dummyList)))
            val pagingData = repository.getMoviePaged()
            pagingData.collect {
                val list = it.collectDataForTest()
                assertNotNull(list)
                assertEquals(dummyList, list)
                assertEquals(dummyList.size, list.size)
            }
        }
    }

    @Test
    fun tvPaged(){
        val dummyList = UtilDataDummy.getTvList()
        runBlocking {
            whenever(repository.getTvPaged()).thenReturn(flowOf(PagingData.from(dummyList)))
            val pagingData = repository.getTvPaged()
            pagingData.collect {
                val list = it.collectDataForTest()
                assertNotNull(list)
                assertEquals(dummyList, list)
                assertEquals(dummyList.size, list.size)
            }
        }
    }

    @Test
    fun getMovieSearchResultPaged(){
        val dummyList = UtilDataDummy.getMovieList()
        runBlocking {
            whenever(repository.getMovieSearchResultPaged(dummyQuery)).thenReturn(flowOf(PagingData.from(dummyList)))
            val pagingData = repository.getMovieSearchResultPaged(dummyQuery)
            pagingData.collect {
                val list = it.collectDataForTest()
                assertNotNull(list)
                assertEquals(dummyList, list)
                assertEquals(dummyList.size, list.size)
            }
        }
    }

    @Test
    fun getTvSearchResultPaged(){
        val dummyList = UtilDataDummy.getTvList()
        runBlocking {
            whenever(repository.getTvSearchResultPaged(dummyQuery)).thenReturn(flowOf(PagingData.from(dummyList)))
            val pagingData = repository.getTvSearchResultPaged(dummyQuery)
            pagingData.collect {
                val list = it.collectDataForTest()
                assertNotNull(list)
                assertEquals(dummyList, list)
                assertEquals(dummyList.size, list.size)
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}