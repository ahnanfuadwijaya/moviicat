package id.riverflows.moviicat.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.riverflows.moviicat.data.source.repository.ListRepository

class HomeSharedViewModel(private val repository: ListRepository): ViewModel() {
    val favoritePaged = repository.getFavoritePaged()

    val moviePaged = repository.getMoviePaged().cachedIn(viewModelScope)

    val tvPaged = repository.getTvPaged().cachedIn(viewModelScope)

    fun movieSearchResultPaged(query: String) = repository.getMovieSearchResultPaged(query).cachedIn(viewModelScope)

    fun tvSearchResultPaged(query: String) = repository.getTvSearchResultPaged(query).cachedIn(viewModelScope)
}