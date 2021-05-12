package id.riverflows.moviicat.data.source.remote.api

import androidx.paging.DataSource
import id.riverflows.moviicat.data.entity.MovieEntity
import id.riverflows.moviicat.data.source.remote.response.MovieListResponse
import id.riverflows.moviicat.data.source.remote.response.TvListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ListApiService {
    @GET("/3/discover/movie")
    suspend fun getMovieList(): MovieListResponse
    @GET("/3/discover/movie")
    suspend fun getPagedMovieList(
        @Query("page")page: Long
    ): MovieListResponse

    @GET("/3/discover/tv")
    suspend fun getTvList(): TvListResponse
    @GET("/3/discover/tv")
    suspend fun getPagedTvList(
            @Query("page")page: Long
    ): TvListResponse
}