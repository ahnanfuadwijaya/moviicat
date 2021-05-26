package id.riverflows.moviicat.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.MovieDetailResponse
import id.riverflows.moviicat.data.source.repository.FakeDetailRepository
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.utils.MainCoroutineScopeRule
import id.riverflows.moviicat.utils.UtilDataDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
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
    private val dummyInsertResult = movieId
    private val dummyRemoveResult = 1
    private val dummyType = UtilConstants.TYPE_MOVIE
    @Mock
    private lateinit var repository: FakeDetailRepository
    @Mock
    private lateinit var observer: Observer<Resource<MovieDetailResponse>>

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun detailMovie() {
        val movieDetail = UtilDataDummy.getDetailMovie(movieId) as MovieDetailResponse
        val dummySuccessResponse: Resource<MovieDetailResponse> = Resource.Success(movieDetail)
        val liveData = MutableLiveData<Resource<MovieDetailResponse>>()
        liveData.observeForever(observer)
        runBlocking {
            `when`(repository.getDetailMovie(movieId)).thenReturn(dummySuccessResponse)
            val response = repository.getDetailMovie(movieId)
            liveData.value = response
            delay(500)
            verify(observer).onChanged(dummySuccessResponse)
            assertNotNull(response)
            assertTrue(liveData.value is Resource.Success)
            assertEquals((liveData.value as Resource.Success).value, movieDetail)
            liveData.removeObserver(observer)
        }
    }

    @Test
    fun insertFavorite(){
        val dummyFavorite = UtilDataDummy.getFavoriteList()[0]
        val observer: Observer<Long> = mock()
        val findResultLiveData = MutableLiveData<Long>()
        findResultLiveData.observeForever(observer)
        runBlocking {
            `when`(repository.insertFavorite(dummyFavorite)).thenReturn(dummyInsertResult)
            val response = repository.insertFavorite(dummyFavorite)
            findResultLiveData.value = response
            delay(500)
            verify(observer).onChanged(response)
            assertNotNull(response)
            assertEquals(findResultLiveData.value, dummyInsertResult)
            findResultLiveData.removeObserver(observer)
        }
    }

    @Test
    fun removeFavorite(){
        val dummyFavorite = UtilDataDummy.getFavoriteList()[0]
        val observer: Observer<Int> = mock()
        val removeResultLiveData = MutableLiveData<Int>()
        removeResultLiveData.observeForever(observer)
        runBlocking {
            `when`(repository.removeFavorite(dummyFavorite)).thenReturn(dummyRemoveResult)
            val response = repository.removeFavorite(dummyFavorite)
            removeResultLiveData.value = response
            delay(500)
            verify(observer).onChanged(response)
            assertNotNull(response)
            assertEquals(removeResultLiveData.value, dummyRemoveResult)
            removeResultLiveData.removeObserver(observer)
        }
    }

    @Test
    fun findFavoriteByIdAndType(){
        val dummyFavorite = UtilDataDummy.getFavoriteByIdAndType(movieId, dummyType) as FavoriteEntity
        val observer: Observer<FavoriteEntity> = mock()
        val removeResultLiveData = MutableLiveData<FavoriteEntity>()
        removeResultLiveData.observeForever(observer)
        runBlocking {
            `when`(repository.findFavoriteByIdAndType(movieId, dummyType)).thenReturn(dummyFavorite)
            val response = repository.findFavoriteByIdAndType(movieId, dummyType)
            removeResultLiveData.value = response
            delay(500)
            verify(observer).onChanged(response)
            assertNotNull(response)
            assertEquals(removeResultLiveData.value, dummyFavorite)
            removeResultLiveData.removeObserver(observer)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}