package id.riverflows.moviicat.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.MovieListResponse
import id.riverflows.moviicat.data.source.remote.response.TvListResponse
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.paging.PagingDataSource
import id.riverflows.moviicat.util.UtilConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeSharedViewModel(private val repository: ListRepository): ViewModel() {
    fun getFavoritePagedList(): LiveData<PagingData<FavoriteEntity>> = Pager(
        PagingConfig(UtilConstants.DATA_PER_PAGE),
        null,
        repository.getFavoritePagedList().asPagingSourceFactory(Dispatchers.IO)
    ).liveData

    val moviePaged = Pager(
        PagingConfig(
            UtilConstants.DATA_PER_PAGE,
            UtilConstants.PREFETCH_DISTANCE,
            true,
            UtilConstants.INITIAL_LOAD_SIZE
        ), UtilConstants.INITIAL_KEY){
        PagingDataSource.MoviePaged(repository)
    }.flow.cachedIn(viewModelScope)

    val tvPaged = Pager(
        PagingConfig(
            UtilConstants.DATA_PER_PAGE,
            UtilConstants.PREFETCH_DISTANCE,
            true,
            UtilConstants.INITIAL_LOAD_SIZE
        ), UtilConstants.INITIAL_KEY){
        PagingDataSource.TvPaged(repository)
    }.flow.cachedIn(viewModelScope)

    fun getMovieSearchResultPaged(query: String) = Pager(
        PagingConfig(
            UtilConstants.DATA_PER_PAGE,
            UtilConstants.PREFETCH_DISTANCE,
            true,
            UtilConstants.INITIAL_LOAD_SIZE
        ), UtilConstants.INITIAL_KEY){
        PagingDataSource.MovieSearchResultPaged(query, repository)
    }.flow.cachedIn(viewModelScope)

    fun getTvSearchResultPaged(query: String) = Pager(
        PagingConfig(
            UtilConstants.DATA_PER_PAGE,
            UtilConstants.PREFETCH_DISTANCE,
            true,
            UtilConstants.INITIAL_LOAD_SIZE
        ), UtilConstants.INITIAL_KEY){
        PagingDataSource.TvSearchResultPaged(query, repository)
    }.flow.cachedIn(viewModelScope)
}