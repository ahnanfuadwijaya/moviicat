package id.riverflows.moviicat.data.source.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import id.riverflows.moviicat.data.entity.MovieEntity
import id.riverflows.moviicat.data.entity.TvEntity
import id.riverflows.moviicat.data.source.local.room.FavoriteDao
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.data.source.remote.api.ListApiService
import id.riverflows.moviicat.paging.PagingDataSource
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.util.UtilQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class FakeListRepository(
    private val api: ListApiService,
    private val favoriteDao: FavoriteDao
): BaseRepository() {
    fun getMoviePaged(): Flow<PagingData<MovieEntity>> {
        return Pager(
            PagingConfig(
                UtilConstants.DATA_PER_PAGE,
                UtilConstants.PREFETCH_DISTANCE,
                true,
                UtilConstants.INITIAL_LOAD_SIZE
            ), UtilConstants.INITIAL_KEY){
            PagingDataSource.MoviePaged(api)
        }.flow
    }

    fun getTvPaged(): Flow<PagingData<TvEntity>> {
        return Pager(
            PagingConfig(
                UtilConstants.DATA_PER_PAGE,
                UtilConstants.PREFETCH_DISTANCE,
                true,
                UtilConstants.INITIAL_LOAD_SIZE
            ), UtilConstants.INITIAL_KEY){
            PagingDataSource.TvPaged(api)
        }.flow
    }

    fun getFavoritePaged(): LiveData<PagingData<FavoriteEntity>> {
        val query = UtilQuery.buildQuery(UtilQuery.GET_ALL_FAVORITE_LIST)
        return Pager(
            PagingConfig(UtilConstants.DATA_PER_PAGE),
            null,
            favoriteDao.getFavoritePaged(query).asPagingSourceFactory(Dispatchers.IO)
        ).liveData
    }

    fun getMovieSearchResultPaged(query: String): Flow<PagingData<MovieEntity>> {
        return Pager(
            PagingConfig(
                UtilConstants.DATA_PER_PAGE,
                UtilConstants.PREFETCH_DISTANCE,
                true,
                UtilConstants.INITIAL_LOAD_SIZE
            ), UtilConstants.INITIAL_KEY){
            PagingDataSource.MovieSearchResultPaged(query, api)
        }.flow
    }

    fun getTvSearchResultPaged(query: String): Flow<PagingData<TvEntity>> {
        return Pager(
            PagingConfig(
                UtilConstants.DATA_PER_PAGE,
                UtilConstants.PREFETCH_DISTANCE,
                true,
                UtilConstants.INITIAL_LOAD_SIZE
            ), UtilConstants.INITIAL_KEY){
            PagingDataSource.TvSearchResultPaged(query, api)
        }.flow
    }
}