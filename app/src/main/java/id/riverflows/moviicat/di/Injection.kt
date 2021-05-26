package id.riverflows.moviicat.di

import android.app.Application
import androidx.lifecycle.ViewModel
import id.riverflows.moviicat.BuildConfig
import id.riverflows.moviicat.data.source.local.room.FavoriteDao
import id.riverflows.moviicat.data.source.local.room.FavoriteDatabase
import id.riverflows.moviicat.data.source.remote.ApiBuilder
import id.riverflows.moviicat.data.source.remote.api.DetailApiService
import id.riverflows.moviicat.data.source.remote.api.ListApiService
import id.riverflows.moviicat.data.source.repository.DetailRepository
import id.riverflows.moviicat.data.source.repository.ListRepository
import id.riverflows.moviicat.ui.detail.movie.DetailMovieViewModel
import id.riverflows.moviicat.ui.detail.tv.DetailTvViewModel
import id.riverflows.moviicat.ui.home.HomeSharedViewModel

object Injection {
    private lateinit var application: Application
    fun init(application: Application) {
        this.application = application
    }
    fun <T : ViewModel?> provideViewModelRepository(modelClass: Class<T>): Any {
        return when{
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailRepository(ApiBuilder.build(DetailApiService::class.java), provideFavoriteDao())
            }
            modelClass.isAssignableFrom(DetailTvViewModel::class.java) ->{
                DetailRepository(ApiBuilder.build(DetailApiService::class.java), provideFavoriteDao())
            }
            modelClass.isAssignableFrom(HomeSharedViewModel::class.java) ->{
                ListRepository(ApiBuilder.build(ListApiService::class.java), provideFavoriteDao())
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
    private fun provideFavoriteDao(): FavoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
    fun provideToken(): String = BuildConfig.TOKEN
    fun provideBaseUrl(): String = BuildConfig.BASE_URL
    fun provideOriginalPosterPath(): String = BuildConfig.ORIGINAL_POSTER_PATH_URL
    fun providePosterPath(): String = BuildConfig.POSTER_PATH_URL
}