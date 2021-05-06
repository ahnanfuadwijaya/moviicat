package id.riverflows.moviicat.di

import androidx.lifecycle.ViewModel
import id.riverflows.moviicat.BuildConfig
import id.riverflows.moviicat.data.source.remote.ApiBuilder
import id.riverflows.moviicat.data.source.remote.api.DetailApiService
import id.riverflows.moviicat.data.source.remote.api.ListApiService
import id.riverflows.moviicat.data.source.repository.BaseRepository
import id.riverflows.moviicat.data.source.repository.DetailRepository
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.ui.detail.movie.DetailMovieViewModel
import id.riverflows.moviicat.ui.detail.tv.DetailTvViewModel
import id.riverflows.moviicat.ui.home.movie.MovieViewModel
import id.riverflows.moviicat.ui.home.tv.TvViewModel

object Injection {
    fun <T : ViewModel?> provideViewModelRepository(modelClass: Class<T>): BaseRepository {
        return when{
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                ListRepository(ApiBuilder.build(ListApiService::class.java))
            }
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                ListRepository(ApiBuilder.build(ListApiService::class.java))
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailRepository(ApiBuilder.build(DetailApiService::class.java))
            }
            modelClass.isAssignableFrom(DetailTvViewModel::class.java) ->{
                DetailRepository(ApiBuilder.build(DetailApiService::class.java))
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
    fun provideToken(): String = BuildConfig.TOKEN
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
    fun provideOriginalPosterPath(): String = BuildConfig.ORIGINAL_POSTER_PATH_URL
    fun providePosterPath(): String = BuildConfig.POSTER_PATH_URL
}