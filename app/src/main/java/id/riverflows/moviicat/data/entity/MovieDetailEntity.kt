package id.riverflows.moviicat.data.entity

data class MovieDetailEntity(
        val id: Int,
        val originalTitle: String,
        val title: String,
        val genres: List<GenreEntity>,
        val overview: String?,
        val popularity: Float,
        val posterPath: String?,
        val releaseDate: String,
        val status: String,
        val voteCount: Int,
        val voteAverage: Float,
)