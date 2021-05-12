package id.riverflows.moviicat.data.source.repository

import id.riverflows.moviicat.data.source.remote.api.ListApiService

class ListRepository(
    private val api: ListApiService
): BaseRepository() {
    suspend fun getMovieList() = safeApiCall {
        api.getMovieList()
    }
    suspend fun getPagedMovieList(page: Long) = safeApiCall {
        api.getPagedMovieList(page)
    }

    suspend fun getTvList() = safeApiCall {
        api.getTvList()
    }
    suspend fun getPagedTvList(page: Long) = safeApiCall {
        api.getPagedTvList(page)
    }
}