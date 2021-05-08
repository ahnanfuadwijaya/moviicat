package id.riverflows.moviicat.data.source.repository

import id.riverflows.moviicat.data.source.remote.api.DetailApiService

class FakeDetailRepository(
    private val api: DetailApiService
): BaseRepository() {
    suspend fun getDetailMovie(movieId: Long) = safeApiCall {
        api.getDetailMovie(movieId)
    }

    suspend fun getDetailTv(tvId: Long) = safeApiCall {
        api.getDetailTv(tvId)
    }
}