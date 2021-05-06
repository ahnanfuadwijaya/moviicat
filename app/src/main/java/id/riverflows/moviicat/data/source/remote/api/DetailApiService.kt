package id.riverflows.moviicat.data.source.remote.api

import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.data.entity.TvDetailEntity
import id.riverflows.moviicat.data.source.remote.response.MovieDetailResponse
import id.riverflows.moviicat.data.source.remote.response.TvDetailResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DetailApiService {
    @GET("/3/movie/{id_movie}")
    suspend fun getDetailMovie(
        @Path("id_movie") movieId: Long
    ): MovieDetailEntity
    @GET("/3/tv/{id_tv}")
    suspend fun getDetailTv(
        @Path("id_tv") tvId: Long
    ): TvDetailEntity
}