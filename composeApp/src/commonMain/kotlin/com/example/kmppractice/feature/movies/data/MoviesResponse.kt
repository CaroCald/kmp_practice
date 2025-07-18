package com.example.kmppractice.feature.movies.data
import com.example.kmppractice.core.base.api_generics.GenericResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class MovieResponse(
    code: Int? = null,
    responseType: String? = null,
    message: String? = null,
    content: MovieContent? = null
) : GenericResponse<MovieContent>(code, responseType, message, content)
@Serializable
data class MovieContent(
    val dates: Dates? = null,
    val page: Long? = null,
    val results: List<Result>? = null,
    val totalPages: Long? = null,
    val totalResults: Long? = null
)
@Serializable
data class Dates(
    val maximum: String? = null,
    val minimum: String? = null
)
@Serializable
data class Result(
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val genreIDS: List<Long>? = null,
    val id: Long? = null,
    val originalLanguage: OriginalLanguage? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Long? = null
)

enum class OriginalLanguage {
    En,
    Es,
    Fr
}
