package id.riverflows.moviicat.data.source.remote.response

import com.google.gson.annotations.SerializedName
import id.riverflows.moviicat.data.entity.GenreEntity

data class TvDetailResponse(
    @field:SerializedName("id")
        val id: Long,
    @field:SerializedName("name")
        val name: String,
    @field:SerializedName("genres")
        val genres: List<GenreEntity>,
    @field:SerializedName("first_air_date")
        val firstAirDate: String,
    @field:SerializedName("last_air_date")
        val lastAirDate: String,
    @field:SerializedName("poster_path")
        val posterPath: String?,
    @field:SerializedName("overview")
        val overview: String,
    @field:SerializedName("popularity")
        val popularity: Float,
    @field:SerializedName("status")
        val status: String,
    @field:SerializedName("vote_average")
        val voteAverage: Float,
)
