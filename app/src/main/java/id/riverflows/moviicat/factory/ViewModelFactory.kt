package id.riverflows.moviicat.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.riverflows.moviicat.data.source.repository.DetailRepository
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.di.Injection
import id.riverflows.moviicat.ui.detail.movie.DetailMovieViewModel
import id.riverflows.moviicat.ui.detail.tv.DetailTvViewModel
import id.riverflows.moviicat.ui.home.HomeSharedViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val repository = Injection.provideViewModelRepository(modelClass)
        return when{
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> DetailMovieViewModel(repository as DetailRepository) as T
            modelClass.isAssignableFrom(DetailTvViewModel::class.java) -> DetailTvViewModel(repository as DetailRepository) as T
            modelClass.isAssignableFrom(HomeSharedViewModel::class.java) -> HomeSharedViewModel(repository as ListRepository) as T
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