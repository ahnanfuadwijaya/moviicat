package id.riverflows.moviicat.data.source.repository

import id.riverflows.moviicat.data.source.local.room.FavoriteDao
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.data.source.remote.api.DetailApiService

class DetailRepository(
    private val api: DetailApiService,
    private val favoriteDao: FavoriteDao
): BaseRepository() {
    suspend fun getDetailMovie(movieId: Long) = safeApiCall {
        api.getDetailMovie(movieId)
    }

    suspend fun getDetailTv(tvId: Long) = safeApiCall {
        api.getDetailTv(tvId)
    }

    fun insertFavorite(data: FavoriteEntity) = favoriteDao.insert(data)
}