package jr.brian.esdecompanionlib.presentation.ui.scraper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import jr.brian.esdecompanionlib.presentation.viewmodel.ScraperViewModel

@Composable
fun ScraperScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScraperViewModel = hiltViewModel(),
    primaryColor: Color = Color(0xFF6200EE),
    backgroundColor: Color = Color(0xFF121212)
) {
    val searchResults by viewModel.searchResults.collectAsState()
    val isScraperActive by viewModel.isScraperActive.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()

    var showSearchDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        if (searchResults != null) {
            ScraperResultsScreen(
                games = searchResults?.games ?: emptyList(),
                images = searchResults?.images ?: emptyList(),
                onBack = {
                    viewModel.clearResults()
                    showSearchDialog = true
                },
                onDownloadImage = { image ->
                    viewModel.downloadImage(image)
                },
                onDownloadAll = {
                    viewModel.downloadAllImages()
                },
                isDownloading = downloadProgress > 0f,
                primaryColor = primaryColor,
                backgroundColor = backgroundColor
            )
        } else {
            showSearchDialog = true
        }

        if (showSearchDialog && searchResults == null) {
            ScraperDialog(
                onDismiss = {
                    showSearchDialog = false
                    onNavigateBack()
                },
                onSearch = { gameName, systemName ->
                    viewModel.searchGame(gameName, systemName)
                    showSearchDialog = false
                },
                isLoading = isScraperActive,
                primaryColor = primaryColor,
                backgroundColor = Color(0xFF1A1A1A)
            )
        }
    }
}
