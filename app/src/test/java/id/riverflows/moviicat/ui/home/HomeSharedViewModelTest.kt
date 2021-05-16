package id.riverflows.moviicat.ui.home

import android.content.ClipData
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import id.riverflows.moviicat.data.entity.MovieEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.MovieListResponse
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.paging.PagingDataSource
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.util.UtilDataDummy
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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
    private lateinit var viewModel: HomeSharedViewModel
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieListResponse>>
    @Mock
    private lateinit var repository: ListRepository
    private val testDispatcher = TestCoroutineDispatcher()
    private val dummyPage = 1
    private val dummyTotalPages = 1
    private val dummyTotalResults = 2
    private val dummyHttpErrorCode = 401

    @Before
    fun setup(){
        viewModel = HomeSharedViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getFavoritePagedList(){
        //TODO Test getFavoritePagedList
    }

    @Test
    fun getMoviePaged(){
        val viewModel: HomeSharedViewModel = mock()
        val dummyList = UtilDataDummy.getMovieList()
        runBlocking {
            whenever(viewModel.moviePaged).thenReturn(flowOf(PagingData.from(dummyList)))
            val pagingData = viewModel.moviePaged
            pagingData.collect {
                val list = it.collectDataForTest()
                assertNotNull(list)
                assertEquals(dummyList, list)
                assertEquals(dummyList.size, list.size)
            }
        }
    }

    @Test
    fun getTvPaged(){
        //TODO Test tvPaged
    }

    @Test
    fun getMovieSearchResultPaged(){
        //TODO Test getMovieSearchResultPaged
    }

    @Test
    fun getTvSearchResultPaged(){
        //TODO Test getTvSearchResultPaged
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    private suspend fun <T : Any> PagingData<T>.collectDataForTest(): List<T> {
        val dcb = object : DifferCallback {
            override fun onChanged(position: Int, count: Int) {}
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
        }
        val items = mutableListOf<T>()
        val dif = object : PagingDataDiffer<T>(dcb, TestCoroutineDispatcher()) {
            override suspend fun presentNewList(
                previousList: NullPaddedList<T>,
                newList: NullPaddedList<T>,
                newCombinedLoadStates: CombinedLoadStates,
                lastAccessedIndex: Int,
                onListPresentable: () -> Unit
            ): Int? {
                for (idx in 0 until newList.size)
                    items.add(newList.getFromStorage(idx))
                onListPresentable()
                return null
            }
        }
        dif.collectFrom(this)
        return items
    }
}