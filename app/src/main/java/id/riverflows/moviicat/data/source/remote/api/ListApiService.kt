package id.riverflows.moviicat.data.source.remote.api

import id.riverflows.moviicat.data.source.remote.response.MovieListResponse
import id.riverflows.moviicat.data.source.remote.response.TvListResponse
import retrofit2.http.GET

interface ListApiService {
    @GET("/3/discover/movie")
    suspend fun getMovieList(): MovieListResponse

    @GET("/3/discover/tv")
    suspend fun getTvList(): TvListResponse
}