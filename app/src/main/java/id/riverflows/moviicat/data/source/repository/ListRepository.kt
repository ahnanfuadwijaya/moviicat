package id.riverflows.moviicat.data.source.repository

import androidx.paging.DataSource
import id.riverflows.moviicat.data.source.local.room.FavoriteDao
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.data.source.remote.api.ListApiService
import id.riverflows.moviicat.util.UtilQuery
import timber.log.Timber

class ListRepository(
    private val api: ListApiService,
    private val favoriteDao: FavoriteDao
): BaseRepository() {
    suspend fun getMovieList() = safeApiCall {
        api.getMovieList()
    }
    suspend fun getMoviePaged(page: Long) = safeApiCall {
        api.getMoviePaged(page)
    }
    suspend fun getTvList() = safeApiCall {
        api.getTvList()
    }
    suspend fun getTvPaged(page: Long) = safeApiCall {
        api.getTvPaged(page)
    }
    fun getFavoritePagedList():  DataSource.Factory<Int, FavoriteEntity>{
        Timber.d("getFav ListRepo")
        val query = UtilQuery.buildQuery(UtilQuery.GET_ALL_FAVORITE_LIST)
        return favoriteDao.getFavoritePagedList(query)
    }
    suspend fun getMovieSearchResultPaged(query: String, page: Long) = safeApiCall {
        api.getMovieSearchResultPaged(query, page)
    }
    suspend fun getTvSearchResultPaged(query: String, page: Long) = safeApiCall {
        api.getTvSearchResultPaged(query, page)
    }
}