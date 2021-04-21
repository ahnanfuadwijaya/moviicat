package id.riverflows.moviicat.ui.detail.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.riverflows.moviicat.data.entity.TvDetailEntity
import id.riverflows.moviicat.util.DataDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailTvViewModel: ViewModel() {
    private val _tv = MutableLiveData<TvDetailEntity>()
    val tv: LiveData<TvDetailEntity> = _tv
    fun getTv(tvId: Int) = viewModelScope.launch(Dispatchers.IO){
        _tv.postValue(DataDummy.getTv(tvId))
    }
}