package id.riverflows.moviicat.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.riverflows.moviicat.data.entity.TvDetailEntity
import id.riverflows.moviicat.util.DataDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TvViewModel: ViewModel() {
    private val _tvList = MutableLiveData<List<TvDetailEntity>>()
    val tvList: LiveData<List<TvDetailEntity>> = _tvList
    fun getTvList() = viewModelScope.launch(Dispatchers.IO){
        _tvList.postValue(DataDummy.getTvList())
    }
}