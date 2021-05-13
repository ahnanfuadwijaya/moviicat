package id.riverflows.moviicat.ui.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.paging.PagingDataSource
import id.riverflows.moviicat.util.UtilConstants

class SearchViewModel(private val repository: ListRepository): ViewModel() {
    val searchPagedResult = Pager(
        PagingConfig(
            UtilConstants.DATA_PER_PAGE,
            UtilConstants.PREFETCH_DISTANCE,
            true,
            UtilConstants.INITIAL_LOAD_SIZE
        ), UtilConstants.INITIAL_KEY){
        PagingDataSource.MovieList(repository)
    }.flow.cachedIn(viewModelScope)
}