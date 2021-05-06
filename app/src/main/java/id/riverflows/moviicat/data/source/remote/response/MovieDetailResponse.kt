package id.riverflows.moviicat.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.riverflows.moviicat.data.entity.GenreEntity
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import kotlinx.parcelize.Parcelize

@Parcelize
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
): Parcelable
