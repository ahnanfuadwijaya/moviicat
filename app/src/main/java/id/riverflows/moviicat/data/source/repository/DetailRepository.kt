package id.riverflows.moviicat.data.source.repository

import id.riverflows.moviicat.BuildConfig
import id.riverflows.moviicat.data.source.remote.api.DetailApiService
import id.riverflows.moviicat.di.Injection

class DetailRepository(
    private val api: DetailApiService
): BaseRepository() {
    suspend fun getDetailMovie(movieId: Long) = safeApiCall {
        api.getDetailMovie(movieId)
    }

    suspend fun getDetailTv(tvId: Long) = safeApiCall {
        api.getDetailTv(tvId)
    }
}