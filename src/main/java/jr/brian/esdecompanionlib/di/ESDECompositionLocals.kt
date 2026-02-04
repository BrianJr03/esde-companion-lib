package jr.brian.esdecompanionlib.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import jr.brian.esdecompanionlib.data.repository.AudioRepository
import jr.brian.esdecompanionlib.data.repository.LocalAudioRepository
import jr.brian.esdecompanionlib.data.repository.LocalScraperRepository
import jr.brian.esdecompanionlib.data.repository.LocalWidgetRepository
import jr.brian.esdecompanionlib.data.repository.ScraperRepository
import jr.brian.esdecompanionlib.data.repository.WidgetRepository

/**
 * Provides ESDE repositories through CompositionLocal.
 * 
 * Note: Repositories are automatically injected by Hilt when used with @HiltViewModel ViewModels.
 * This function is optional and only needed if you want to access repositories directly in Composables
 * without using ViewModels.
 * 
 * @param widgetRepository The widget repository instance
 * @param audioRepository The audio repository instance
 * @param scraperRepository The scraper repository instance
 * @param content The composable content
 */
@Composable
fun ProvideESDERepositories(
    widgetRepository: WidgetRepository,
    audioRepository: AudioRepository,
    scraperRepository: ScraperRepository,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalWidgetRepository provides widgetRepository,
        LocalAudioRepository provides audioRepository,
        LocalScraperRepository provides scraperRepository,
        content = content
    )
}
