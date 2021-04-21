package id.riverflows.moviicat.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.util.DataDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMovieViewModel: ViewModel() {
    private val _movie = MutableLiveData<MovieDetailEntity>()
    val movie: LiveData<MovieDetailEntity> =_movie
    fun getMovie(movieId: Int) = viewModelScope.launch(Dispatchers.IO){
        _movie.postValue(DataDummy.getMovie(movieId))
    }
}