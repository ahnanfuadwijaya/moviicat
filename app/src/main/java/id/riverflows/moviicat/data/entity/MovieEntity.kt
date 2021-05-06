package id.riverflows.moviicat.data.entity

import com.google.gson.annotations.SerializedName

data class MovieEntity (
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("vote_average")
    val voteAverage: Float,
    @field:SerializedName("release_date")
    val releaseDate: String,
    @field:SerializedName("poster_path")
    val posterPath: String
)