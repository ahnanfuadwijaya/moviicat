package id.riverflows.moviicat.data.source.repository

import id.riverflows.moviicat.data.source.remote.api.ListApiService

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