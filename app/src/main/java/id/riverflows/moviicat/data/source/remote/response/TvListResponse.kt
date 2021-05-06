package id.riverflows.moviicat.data.source.remote.response

import com.google.gson.annotations.SerializedName
import id.riverflows.moviicat.data.entity.TvEntity

data class TvListResponse(
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("results")
    val data: List<TvEntity>,
    @field:SerializedName("total_pages")
    val totalPages: Int,
    @field:SerializedName("total_results")
    val totalResults: Int
)
