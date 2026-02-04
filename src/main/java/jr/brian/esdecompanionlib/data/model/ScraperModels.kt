package jr.brian.esdecompanionlib.data.model

enum class ScraperSource {
    STEAM_GRID_DB,
    IGDB,
    LAUNCHBOX,
    MANUAL
}

data class ScraperGame(
    val id: String,
    val title: String,
    val platform: String,
    val releaseDate: String? = null,
    val developer: String? = null,
    val publisher: String? = null,
    val genre: String? = null,
    val description: String? = null,
    val rating: Float? = null,
    val coverUrl: String? = null,
    val source: ScraperSource
)

data class ScraperImage(
    val id: String,
    val gameId: String,
    val imageType: ContentType,
    val url: String,
    val thumbnailUrl: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val source: ScraperSource
)

data class ScraperQuery(
    val gameName: String,
    val systemName: String,
    val year: String? = null,
    val preferredSource: ScraperSource? = null
)

data class ScraperResult(
    val query: ScraperQuery,
    val games: List<ScraperGame> = emptyList(),
    val images: List<ScraperImage> = emptyList(),
    val success: Boolean = true,
    val errorMessage: String? = null
)

data class ScraperConfig(
    val steamGridApiKey: String? = null,
    val igdbClientId: String? = null,
    val igdbClientSecret: String? = null,
    val launchBoxEnabled: Boolean = true,
    val preferredSource: ScraperSource = ScraperSource.IGDB,
    val autoDownload: Boolean = false
)
