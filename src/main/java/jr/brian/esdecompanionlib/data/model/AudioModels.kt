package jr.brian.esdecompanionlib.data.model

enum class AudioSource {
    WIDGET,
    BACKGROUND,
    MUSIC,
    NONE
}

data class AudioState(
    val currentSource: AudioSource = AudioSource.NONE,
    val isMenuActive: Boolean = false,
    val activeWidgetIds: Set<String> = emptySet(),
    val isBackgroundActive: Boolean = false,
    val volume: Float = 1.0f
)

data class AudioNormalizationResult(
    val filePath: String,
    val originalLufs: Double,
    val targetLufs: Double,
    val gainAdjustment: Float,
    val peakLevel: Double,
    val success: Boolean,
    val errorMessage: String? = null
)
