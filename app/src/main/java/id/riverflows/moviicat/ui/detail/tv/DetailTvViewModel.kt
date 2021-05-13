package id.riverflows.moviicat.ui.detail.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.TvDetailResponse
import id.riverflows.moviicat.data.source.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailTvViewModel(private val repository: DetailRepository): ViewModel() {
    private val _tv = MutableLiveData<Resource<TvDetailResponse>>()
    val tv: LiveData<Resource<TvDetailResponse>> = _tv
    private val _insertResult = MutableLiveData<Long>()
    val insertResult: LiveData<Long> = _insertResult
    fun getTv(tvId: Long) = viewModelScope.launch(Dispatchers.IO){
        _tv.postValue(repository.getDetailTv(tvId))
    }
    fun insertFavorite(data: FavoriteEntity) = viewModelScope.launch(Dispatchers.IO) {
        _insertResult.postValue(repository.insertFavorite(data))
    }
}