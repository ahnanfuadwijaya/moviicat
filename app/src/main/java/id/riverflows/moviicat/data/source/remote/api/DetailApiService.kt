package id.riverflows.moviicat.data.source.remote.api

import id.riverflows.moviicat.data.source.remote.response.MovieDetailResponse
import id.riverflows.moviicat.data.source.remote.response.TvDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApiService {
    @GET("/3/movie/{id_movie}")
    suspend fun getDetailMovie(
        @Path("id_movie") movieId: Long
    ): MovieDetailResponse
    @GET("/3/tv/{id_tv}")
    suspend fun getDetailTv(
        @Path("id_tv") tvId: Long
    ): TvDetailResponse
}