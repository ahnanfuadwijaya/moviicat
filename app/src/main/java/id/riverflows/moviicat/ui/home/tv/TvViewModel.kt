package id.riverflows.moviicat.ui.home.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.riverflows.moviicat.data.entity.TvDetailEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.MovieListResponse
import id.riverflows.moviicat.data.source.remote.response.TvListResponse
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.util.DataDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvViewModel(private val repository: ListRepository): ViewModel() {
    private val _tvList = MutableLiveData<Resource<TvListResponse>>()
    val tvList: LiveData<Resource<TvListResponse>> = _tvList
    fun getTvList() = viewModelScope.launch(Dispatchers.IO){
        _tvList.postValue(repository.getTvList())
    }
}