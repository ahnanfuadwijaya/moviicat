package id.riverflows.moviicat.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailEntity(
        val id: Int,
        val title: String,
        val genres: List<GenreEntity>,
        val overview: String?,
        val popularity: Float,
        val posterPath: String?,
        val releaseDate: String,
        val status: String,
        val voteAverage: Float,
): Parcelable