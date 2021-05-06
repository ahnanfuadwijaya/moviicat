package id.riverflows.moviicat.data.source.remote.response

import com.google.gson.annotations.SerializedName
import id.riverflows.moviicat.data.entity.MovieEntity

data class MovieListResponse(
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("results")
    val data: List<MovieEntity>,
    @field:SerializedName("total_pages")
    val totalPages: Int,
    @field:SerializedName("total_results")
    val totalResults: Int
)
