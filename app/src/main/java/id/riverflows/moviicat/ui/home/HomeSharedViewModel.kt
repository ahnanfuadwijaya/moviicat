package id.riverflows.moviicat.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.liveData
import id.riverflows.moviicat.data.source.repository.ListRepository

class HomeSharedViewModel(private val repository: ListRepository): ViewModel() {
    val favoritePaged = repository.getFavoritePaged()

    val moviePaged = repository.getMoviePaged().cachedIn(viewModelScope)

    val tvPaged = repository.getTvPaged().cachedIn(viewModelScope)

    fun getMovieSearchResultPaged(query: String) = repository.getMovieSearchResultPaged(query).cachedIn(viewModelScope)

    fun getTvSearchResultPaged(query: String) = repository.getTvSearchResultPaged(query).cachedIn(viewModelScope)
}