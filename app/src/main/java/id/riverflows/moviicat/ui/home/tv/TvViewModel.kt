package id.riverflows.moviicat.ui.home.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.TvListResponse
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.paging.PagingDataSource
import id.riverflows.moviicat.util.UtilConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvViewModel(private val repository: ListRepository): ViewModel() {
    private val _tvList = MutableLiveData<Resource<TvListResponse>>()
    val tvList: LiveData<Resource<TvListResponse>> = _tvList
    fun getTvList() = viewModelScope.launch(Dispatchers.IO){
        _tvList.postValue(repository.getTvList())
    }
    val tvPagedList = Pager(
        PagingConfig(
            UtilConstants.DATA_PER_PAGE,
            UtilConstants.PREFETCH_DISTANCE,
            true,
            UtilConstants.INITIAL_LOAD_SIZE
        ), UtilConstants.INITIAL_KEY){
        PagingDataSource.TvList(repository)
    }.flow.cachedIn(viewModelScope)
}