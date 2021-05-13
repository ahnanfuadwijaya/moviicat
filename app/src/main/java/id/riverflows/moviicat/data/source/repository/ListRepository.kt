package id.riverflows.moviicat.data.source.repository

import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
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
    suspend fun getPagedMovieList(page: Long) = safeApiCall {
        api.getPagedMovieList(page)
    }
    suspend fun getTvList() = safeApiCall {
        api.getTvList()
    }
    suspend fun getPagedTvList(page: Long) = safeApiCall {
        api.getPagedTvList(page)
    }
    fun getFavoritePagedList():  DataSource.Factory<Int, FavoriteEntity>{
        Timber.d("getFav ListRepo")
        val query = UtilQuery.buildQuery(UtilQuery.GET_ALL_FAVORITE_LIST)
        return favoriteDao.getFavoritePagedList(query)
    }
}