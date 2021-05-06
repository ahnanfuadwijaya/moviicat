package id.riverflows.moviicat.ui.detail.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.riverflows.moviicat.data.entity.TvDetailEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.TvDetailResponse
import id.riverflows.moviicat.data.source.repository.DetailRepository
import id.riverflows.moviicat.util.DataDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailTvViewModel(private val repository: DetailRepository): ViewModel() {
    private val _tv = MutableLiveData<Resource<TvDetailEntity>>()
    val tv: LiveData<Resource<TvDetailEntity>> = _tv
    fun getTv(tvId: Long) = viewModelScope.launch(Dispatchers.IO){
        _tv.postValue(repository.getDetailTv(tvId))
    }
}