package id.riverflows.moviicat.ui.home.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.util.DataDummy
import kotlinx.coroutines.launch

class MovieViewModel: ViewModel() {
    private val _movieList = MutableLiveData<List<MovieDetailEntity>>()
    val movieList: LiveData<List<MovieDetailEntity>> = _movieList
    fun getMovieList() = viewModelScope.launch {
        _movieList.postValue(DataDummy.getMovieList())
    }
}