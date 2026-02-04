package jr.brian.esdecompanionlib.data.model

import androidx.compose.ui.graphics.Color

/**
 * State for wallpaper display including current media, effects, and playback.
 */
data class WallpaperState(
    val currentImagePath: String? = null,
    val marqueePath: String? = null,
    val videoPath: String? = null,
    val isVideoPlaying: Boolean = false,
    val videoAudioEnabled: Boolean = false,
    val videoDelaySeconds: Int = 3,
    val dimmingLevel: Float = 0.2f,
    val blurLevel: Float = 0f,
    val animationStyle: AnimationStyle = AnimationStyle.Fade,
    val animationDuration: Int = 300,
    val animationScale: Float = 0.9f,
    val backgroundColor: Color = Color.Black,
    val showSystemLogo: Boolean = true,
    val logoAlignment: LogoAlignment = LogoAlignment.Center,
    val hideContentOnVideo: Boolean = false
)
