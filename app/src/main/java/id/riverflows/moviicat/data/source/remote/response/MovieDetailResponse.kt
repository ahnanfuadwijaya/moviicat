package id.riverflows.moviicat.data.source.remote.response

import com.google.gson.annotations.SerializedName
import id.riverflows.moviicat.data.entity.GenreEntity

data class MovieDetailResponse(
    @field:SerializedName("id")
        val id: Long,
    @field:SerializedName("title")
        val title: String,
    @field:SerializedName("genres")
        val genres: List<GenreEntity>,
    @field:SerializedName("overview")
        val overview: String?,
    @field:SerializedName("popularity")
        val popularity: Float,
    @field:SerializedName("poster_path")
        val posterPath: String?,
    @field:SerializedName("release_date")
        val releaseDate: String,
    @field:SerializedName("status")
        val status: String,
    @field:SerializedName("vote_average")
        val voteAverage: Float,
)