package id.riverflows.moviicat.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.riverflows.moviicat.data.source.remote.ApiBuilder
import id.riverflows.moviicat.data.source.remote.api.DetailApiService
import id.riverflows.moviicat.data.source.remote.api.ListApiService
import id.riverflows.moviicat.data.source.repository.BaseRepository
import id.riverflows.moviicat.data.source.repository.DetailRepository
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.di.Injection
import id.riverflows.moviicat.ui.detail.movie.DetailMovieViewModel
import id.riverflows.moviicat.ui.detail.tv.DetailTvViewModel
import id.riverflows.moviicat.ui.home.movie.MovieViewModel
import id.riverflows.moviicat.ui.home.tv.TvViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val repository = Injection.provideViewModelRepository(modelClass)
        return when{
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(repository as ListRepository) as T
            modelClass.isAssignableFrom(TvViewModel::class.java) -> TvViewModel(repository as ListRepository) as T
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> DetailMovieViewModel(repository as DetailRepository) as T
            modelClass.isAssignableFrom(DetailTvViewModel::class.java) -> DetailTvViewModel(repository as DetailRepository) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory().apply {
                    instance = this
                }
            }
    }
}