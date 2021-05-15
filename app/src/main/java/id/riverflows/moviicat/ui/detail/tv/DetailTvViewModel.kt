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
    private val _removeResult = MutableLiveData<Int>()
    val removeResult: LiveData<Int> = _removeResult
    private val _isFavorite = MutableLiveData<FavoriteEntity>()
    val isFavorite: LiveData<FavoriteEntity> = _isFavorite
    fun getTv(tvId: Long) = viewModelScope.launch(Dispatchers.IO){
        _tv.postValue(repository.getDetailTv(tvId))
    }
    fun insertFavorite(data: FavoriteEntity) = viewModelScope.launch(Dispatchers.IO) {
        _insertResult.postValue(repository.insertFavorite(data))
    }
    fun removeFavorite(data: FavoriteEntity) = viewModelScope.launch(Dispatchers.IO){
        _removeResult.postValue(repository.removeFavorite(data))
    }
    fun findFavoriteByIdAndType(id: Long, type: String) = viewModelScope.launch(Dispatchers.IO){
        _isFavorite.postValue(repository.findFavoriteByIdAndType(id, type))
    }
}