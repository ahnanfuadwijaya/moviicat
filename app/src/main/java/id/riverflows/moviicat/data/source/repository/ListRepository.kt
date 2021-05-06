package id.riverflows.moviicat.data.source.repository

import android.content.SharedPreferences
import id.riverflows.moviicat.BuildConfig
import id.riverflows.moviicat.data.source.remote.api.ListApiService
import id.riverflows.moviicat.di.Injection

class ListRepository(
    private val api: ListApiService
): BaseRepository() {
    suspend fun getMovieList() = safeApiCall {
        api.getMovieList()
    }
    suspend fun getTvList() = safeApiCall {
        api.getTvList()
    }
}