package id.riverflows.moviicat.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.riverflows.moviicat.data.entity.MovieEntity
import id.riverflows.moviicat.data.entity.TvEntity
import id.riverflows.moviicat.data.source.remote.api.ListApiService

class PagingDataSource{
    class MoviePaged(private val api: ListApiService): PagingSource<Long, MovieEntity>() {
        override suspend fun load(params: LoadParams<Long>): LoadResult<Long, MovieEntity> {
            return try {
                val page = params.key ?: 1L
                val response = api.getMoviePaged(page)
                LoadResult.Page(
                    data = response.data,
                    prevKey = if (page > 1) page - 1 else null,
                    nextKey = if (page < response.totalPages) page + 1 else null
                )
            }catch (exception: Exception) {
                LoadResult.Error(exception)
            }
        }

        override fun getRefreshKey(state: PagingState<Long, MovieEntity>): Long? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestItemToPosition(anchorPosition)?.id
            }
        }
    }
    class TvPaged(private val api: ListApiService): PagingSource<Long, TvEntity>(){
        override fun getRefreshKey(state: PagingState<Long, TvEntity>): Long? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestItemToPosition(anchorPosition)?.id
            }
        }

        override suspend fun load(params: LoadParams<Long>): LoadResult<Long, TvEntity> {
            return try {
                val page = params.key ?: 1L
                val response = api.getTvPaged(page)
                LoadResult.Page(
                    data = response.data,
                    prevKey = if (page > 1) page - 1 else null,
                    nextKey = if (page < response.totalPages) page + 1 else null
                )
            }catch (exception: Exception) {
                LoadResult.Error(exception)
            }
        }
    }

    class MovieSearchResultPaged(
        private val query: String,
        private val api: ListApiService): PagingSource<Long, MovieEntity>() {

        override suspend fun load(params: LoadParams<Long>): LoadResult<Long, MovieEntity> {
            return try {
                val page = params.key ?: 1L
                val response = api.getMovieSearchResultPaged(query, page)
                LoadResult.Page(
                    data = response.data,
                    prevKey = if (page > 1) page - 1 else null,
                    nextKey = if (page < response.totalPages) page + 1 else null
                )
            }catch (exception: Exception) {
                LoadResult.Error(exception)
            }
        }

        override fun getRefreshKey(state: PagingState<Long, MovieEntity>): Long? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestItemToPosition(anchorPosition)?.id
            }
        }
    }

    class TvSearchResultPaged(
        private val query: String,
        private val api: ListApiService): PagingSource<Long, TvEntity>() {

        override suspend fun load(params: LoadParams<Long>): LoadResult<Long, TvEntity> {
            return try {
                val page = params.key ?: 1L
                val response = api.getTvSearchResultPaged(query, page)
                LoadResult.Page(
                    data = response.data,
                    prevKey = if (page > 1) page - 1 else null,
                    nextKey = if (page < response.totalPages) page + 1 else null
                )
            }catch (exception: Exception) {
                LoadResult.Error(exception)
            }
        }

        override fun getRefreshKey(state: PagingState<Long, TvEntity>): Long? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestItemToPosition(anchorPosition)?.id
            }
        }
    }
}