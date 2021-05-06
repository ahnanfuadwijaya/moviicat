package id.riverflows.moviicat.ui.home.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.MovieListResponse
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.util.DataDummy
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: ListRepository): ViewModel() {
    private val _movieList = MutableLiveData<Resource<MovieListResponse>>()
    val movieList: LiveData<Resource<MovieListResponse>> = _movieList
    fun getMovieList() = viewModelScope.launch {
        _movieList.postValue(repository.getMovieList())
    }
}