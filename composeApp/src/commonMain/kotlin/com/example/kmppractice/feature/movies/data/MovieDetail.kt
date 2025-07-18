package com.example.kmppractice.feature.movies.data

import com.example.kmppractice.core.base.api_generics.GenericResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class MovieDetail(
    code: Int? = null,
    responseType: String? = null,
    message: String? = null,
    content: MovieDetailContent? = null
) : GenericResponse<MovieDetailContent>(code, responseType, message, content)
@Serializable
data class MovieDetailContent (
    val adult: Boolean = false,

    @SerialName("backdrop_path")
    val backdropPath: String = "",

    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection = BelongsToCollection(),

    val budget: Long = 0L,
    val genres: List<Genre> = emptyList(),
    val homepage: String = "",
    val id: Long = 0L,

    @SerialName("imdb_id")
    val imdbID: String = "",

    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),

    @SerialName("original_language")
    val originalLanguage: String = "",

    @SerialName("original_title")
    val originalTitle: String = "",

    val overview: String = "",
    val popularity: Double = 0.0,

    @SerialName("poster_path")
    val posterPath: String = "",

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany> = emptyList(),

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry> = emptyList(),

    @SerialName("release_date")
    val releaseDate: String = "",

    val revenue: Long = 0L,
    val runtime: Long = 0L,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage> = emptyList(),

    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Long = 0L
)

@Serializable
data class BelongsToCollection (
    val id: Long = 0L,
    val name: String = "",

    @SerialName("poster_path")
    val posterPath: String = "",

    @SerialName("backdrop_path")
    val backdropPath: String = ""
)

@Serializable
data class Genre (
    val id: Long = 0L,
    val name: String = ""
)

@Serializable
data class ProductionCompany (
    val id: Long = 0L,

    @SerialName("logo_path")
    val logoPath: String = "",

    val name: String = "",

    @SerialName("origin_country")
    val originCountry: String = ""
)

@Serializable
data class ProductionCountry (
    @SerialName("iso_3166_1")
    val iso3166_1: String = "",

    val name: String = ""
)

@Serializable
data class SpokenLanguage (
    @SerialName("english_name")
    val englishName: String = "",

    @SerialName("iso_639_1")
    val iso639_1: String = "",

    val name: String = ""
)
