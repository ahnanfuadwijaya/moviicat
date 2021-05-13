package id.riverflows.moviicat.ui.home.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.util.UtilConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class FavoriteViewModel(private val repository: ListRepository): ViewModel() {
    fun getFavoritePagedList(): LiveData<PagingData<FavoriteEntity>> = Pager(
        PagingConfig(UtilConstants.DATA_PER_PAGE),
        null,
        repository.getFavoritePagedList().asPagingSourceFactory(Dispatchers.IO)
    ).liveData
}