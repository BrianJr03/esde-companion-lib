package jr.brian.esdecompanionlib.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jr.brian.esdecompanionlib.data.model.ScraperConfig
import jr.brian.esdecompanionlib.data.model.ScraperQuery
import jr.brian.esdecompanionlib.data.model.ScraperResult
import jr.brian.esdecompanionlib.data.model.ScraperSource
import jr.brian.esdecompanionlib.data.repository.ScraperRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScraperViewModel @Inject constructor(
    private val scraperRepository: ScraperRepository
) : ViewModel() {
    
    private val _searchResults = MutableStateFlow<ScraperResult?>(null)
    val searchResults: StateFlow<ScraperResult?> = _searchResults.asStateFlow()
    
    private val _isScraperActive = MutableStateFlow(false)
    val isScraperActive: StateFlow<Boolean> = _isScraperActive.asStateFlow()
    
    private val _downloadProgress = MutableStateFlow(0f)
    val downloadProgress: StateFlow<Float> = _downloadProgress.asStateFlow()
    
    fun searchGame(gameName: String, systemName: String) {
        viewModelScope.launch {
            _isScraperActive.value = true
            try {
                val query = ScraperQuery(
                    gameName = gameName,
                    systemName = systemName
                )
                val result = scraperRepository.searchGame(query)
                _searchResults.value = result
            } catch (e: Exception) {
                _searchResults.value = ScraperResult(
                    query = ScraperQuery(gameName, systemName),
                    success = false,
                    errorMessage = e.message
                )
            } finally {
                _isScraperActive.value = false
            }
        }
    }
    
    fun downloadImage(image: jr.brian.esdecompanionlib.data.model.ScraperImage) {
        viewModelScope.launch {
            _downloadProgress.value = 0.5f
            try {
                scraperRepository.downloadImage(image)
            } catch (e: Exception) {
                // Handle error
            } finally {
                _downloadProgress.value = 0f
            }
        }
    }
    
    fun downloadAllImages() {
        viewModelScope.launch {
            val images = _searchResults.value?.images ?: return@launch
            _downloadProgress.value = 0.5f
            try {
                images.forEach { image ->
                    scraperRepository.downloadImage(image)
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _downloadProgress.value = 0f
            }
        }
    }
    
    fun clearResults() {
        _searchResults.value = null
    }
}
