package id.riverflows.moviicat.ui.home.movie

import androidx.lifecycle.*
import androidx.paging.*
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.MovieListResponse
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.paging.PagingDataSource
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.util.UtilConstants.DATA_PER_PAGE
import id.riverflows.moviicat.util.UtilConstants.INITIAL_LOAD_SIZE
import id.riverflows.moviicat.util.UtilConstants.PREFETCH_DISTANCE
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: ListRepository): ViewModel() {
    private val _movieList = MutableLiveData<Resource<MovieListResponse>>()
    val movieList: LiveData<Resource<MovieListResponse>> = _movieList
    val moviePaged = Pager(
            PagingConfig(
                    DATA_PER_PAGE,
                    PREFETCH_DISTANCE,
                    true,
                    INITIAL_LOAD_SIZE
            ), UtilConstants.INITIAL_KEY){
        PagingDataSource.MoviePaged(repository)
    }.flow.cachedIn(viewModelScope)

    fun getMovieList() = viewModelScope.launch {
        _movieList.postValue(repository.getMovieList())
    }
}