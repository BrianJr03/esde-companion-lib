package jr.brian.esdecompanionlib.data.repository

import androidx.compose.runtime.staticCompositionLocalOf

val LocalWidgetRepository = staticCompositionLocalOf<WidgetRepository> {
    error("No WidgetRepository provided")
}

val LocalAudioRepository = staticCompositionLocalOf<AudioRepository> {
    error("No AudioRepository provided")
}

val LocalScraperRepository = staticCompositionLocalOf<ScraperRepository> {
    error("No ScraperRepository provided")
}
