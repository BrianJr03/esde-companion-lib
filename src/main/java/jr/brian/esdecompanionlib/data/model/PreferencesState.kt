package jr.brian.esdecompanionlib.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

/**
 * Preferred media type for system background images.
 */
enum class SystemImageType(val folderName: String?) {
    None(null),
    Fanart("fanart"),
    Screenshots("screenshots"),
    TitleScreens("titlescreens")
}

/**
 * Preferred media type for game background images.
 */
enum class GameImageType(val folderName: String?) {
    None(null),
    Screenshots("screenshots"),
    Fanart("fanart"),
    TitleScreens("titlescreens"),
    Covers("covers"),
    MixImages("miximages")
}

/**
 * Alignment position for the system logo overlay.
 */
enum class LogoAlignment {
    Top,
    Center,
    Bottom
}

/**
 * Complete state of ESDE companion preferences.
 */
data class ESDEPrefsState(
    val animationStyle: AnimationStyle = AnimationStyle.Fade,
    val animationDuration: Int = 300,
    val animationScale: Float = 0.9f,
    val blurLevel: Int = 0,
    val dimmingLevel: Int = 20,
    val backgroundColor: Int = Color.Black.toArgb(),
    val videoEnabled: Boolean = false,
    val videoDelaySeconds: Int = 3,
    val videoAudioEnabled: Boolean = false,
    val esdeEnabled: Boolean = false,
    val lastSelectedSystem: String? = null,
    val systemImageType: SystemImageType = SystemImageType.Fanart,
    val gameImageType: GameImageType = GameImageType.Screenshots,
    val showSystemLogo: Boolean = true,
    val logoAlignment: LogoAlignment = LogoAlignment.Center,
    val randomSystemImage: Boolean = false,
    val hideContentOnVideo: Boolean = false,
    val widgetPaginationEnabled: Boolean = false,
    val maxWidgetsPerPage: Int = 4,
    val audioNormalizationEnabled: Boolean = false,
    val targetLufs: Double = -16.0,
    val scraperEnabled: Boolean = false,
    val scraperAutoDownload: Boolean = false,
    val scraperPreferredSource: String = "IGDB",
    val steamGridApiKey: String = "",
    val igdbClientId: String = "",
    val igdbClientSecret: String = ""
) {
    /** Dimming level as float (0.0 to 1.0) */
    val dimmingLevelFloat: Float get() = dimmingLevel / 100f
}
