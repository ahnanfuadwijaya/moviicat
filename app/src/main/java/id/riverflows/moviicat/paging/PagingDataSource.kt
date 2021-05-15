package id.riverflows.moviicat.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.riverflows.moviicat.data.entity.MovieEntity
import id.riverflows.moviicat.data.entity.TvEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.repository.ListRepository

class PagingDataSource{
    class MoviePaged(private val repository: ListRepository): PagingSource<Long, MovieEntity>() {
        override suspend fun load(params: LoadParams<Long>): LoadResult<Long, MovieEntity> {
            val page = params.key ?: 1L
            val response = repository.getMoviePaged(page)
                    return try {
                        when(response){
                            is Resource.Success -> {
                                LoadResult.Page(
                                    data = response.value.data,
                                    prevKey = if (page > 1) page - 1 else null,
                                    nextKey = if (page < response.value.totalPages) page + 1 else null
                                )
                            }
                            is Resource.Failure -> {
                                LoadResult.Error(Exception())
                            }
                        }
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
    class TvPaged(private val repository: ListRepository): PagingSource<Long, TvEntity>(){
        override fun getRefreshKey(state: PagingState<Long, TvEntity>): Long? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestItemToPosition(anchorPosition)?.id
            }
        }

        override suspend fun load(params: LoadParams<Long>): LoadResult<Long, TvEntity> {
            val page = params.key ?: 1L
            val response = repository.getTvPaged(page)
            return try {
                when(response){
                    is Resource.Success -> {
                        LoadResult.Page(
                            data = response.value.data,
                            prevKey = if (page > 1) page - 1 else null,
                            nextKey = if (page < response.value.totalPages) page + 1 else null
                        )
                    }
                    is Resource.Failure -> {
                        LoadResult.Error(Exception())
                    }
                }
            }catch (exception: Exception) {
                LoadResult.Error(exception)
            }
        }
    }
    class MovieSearchResultPaged(
        private val query: String,
        private val repository: ListRepository): PagingSource<Long, MovieEntity>() {

        override suspend fun load(params: LoadParams<Long>): LoadResult<Long, MovieEntity> {
            val page = params.key ?: 1L
            val response = repository.getMovieSearchResultPaged(query, page)
            return try {
                when(response){
                    is Resource.Success -> {
                        LoadResult.Page(
                            data = response.value.data,
                            prevKey = if (page > 1) page - 1 else null,
                            nextKey = if (page < response.value.totalPages) page + 1 else null
                        )
                    }
                    is Resource.Failure -> {
                        LoadResult.Error(Exception())
                    }
                }
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
        private val repository: ListRepository): PagingSource<Long, TvEntity>() {

        override suspend fun load(params: LoadParams<Long>): LoadResult<Long, TvEntity> {
            val page = params.key ?: 1L
            val response = repository.getTvSearchResultPaged(query, page)
            return try {
                when(response){
                    is Resource.Success -> {
                        LoadResult.Page(
                            data = response.value.data,
                            prevKey = if (page > 1) page - 1 else null,
                            nextKey = if (page < response.value.totalPages) page + 1 else null
                        )
                    }
                    is Resource.Failure -> {
                        LoadResult.Error(Exception())
                    }
                }
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