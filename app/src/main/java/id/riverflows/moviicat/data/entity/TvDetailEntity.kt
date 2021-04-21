package id.riverflows.moviicat.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvDetailEntity(
        val id: Int,
        val name: String,
        val genres: List<GenreEntity>,
        val firstAirDate: String,
        val lastAirDate: String,
        val posterPath: String?,
        val overview: String,
        val popularity: Float,
        val status: String,
        val voteAverage: Float,
): Parcelable
