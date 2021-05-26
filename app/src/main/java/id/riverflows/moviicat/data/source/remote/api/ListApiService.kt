package id.riverflows.moviicat.data.source.remote.api

import id.riverflows.moviicat.data.source.remote.response.MovieListResponse
import id.riverflows.moviicat.data.source.remote.response.TvListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ListApiService {
    @GET("/3/discover/movie")
    suspend fun getMoviePaged(
        @Query("page")page: Long
    ): MovieListResponse

    @GET("/3/discover/tv")
    suspend fun getTvPaged(
            @Query("page")page: Long
    ): TvListResponse
    @GET("/3/search/movie")
    suspend fun getMovieSearchResultPaged(
        @Query("query")query: String,
        @Query("page")page: Long
    ): MovieListResponse
    @GET("/3/search/tv")
    suspend fun getTvSearchResultPaged(
        @Query("query")query: String,
        @Query("page")page: Long
    ): TvListResponse
}