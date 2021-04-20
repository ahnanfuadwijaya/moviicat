package id.riverflows.moviicat.data.entity

data class TvDetailEntity(
        val id: Int,
        val originalName: String,
        val name: String,
        val genres: List<GenreEntity>,
        val firstAirDate: String,
        val lastAirDate: String,
        val posterPath: String?,
        val overview: String,
        val popularity: Float,
        val status: String,
        val voteCount: Int,
        val voteAverage: Float,
)
