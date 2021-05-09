package id.riverflows.moviicat.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.MovieDetailResponse
import id.riverflows.moviicat.data.source.repository.DetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val repository: DetailRepository): ViewModel() {
    private val _movie = MutableLiveData<Resource<MovieDetailResponse>>()
    val movie: LiveData<Resource<MovieDetailResponse>> =_movie
    fun getMovie(movieId: Long) = viewModelScope.launch(Dispatchers.IO){
        _movie.postValue(repository.getDetailMovie(movieId))
    }
}