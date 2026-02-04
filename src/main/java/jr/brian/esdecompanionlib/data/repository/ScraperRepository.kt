package jr.brian.esdecompanionlib.data.repository

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import jr.brian.esdecompanionlib.data.model.ContentType
import jr.brian.esdecompanionlib.data.model.ScraperImage
import jr.brian.esdecompanionlib.data.model.ScraperQuery
import jr.brian.esdecompanionlib.data.model.ScraperResult
import jr.brian.esdecompanionlib.util.ESDEMediaConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScraperRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferencesRepository: PreferencesRepository
) {
    
    private val httpClient = OkHttpClient.Builder()
        .followRedirects(true)
        .followSslRedirects(true)
        .build()
    
    companion object {
        private const val TAG = "ScraperRepository"
    }
    
    suspend fun searchGame(query: ScraperQuery): ScraperResult {
        // TODO: Implement actual scraper API calls
        // For now, return empty result
        return ScraperResult(
            query = query,
            games = emptyList(),
            images = emptyList(),
            success = true
        )
    }
    
    /**
     * Downloads an image from the scraper and saves it to the appropriate
     * ES-DE downloaded_media folder based on the image type.
     */
    suspend fun downloadImage(image: ScraperImage) = withContext(Dispatchers.IO) {
        try {
            // Determine the target folder based on content type
            val folderName = when (image.imageType) {
                ContentType.FANART -> ESDEMediaConstants.FOLDER_FANART
                ContentType.SCREENSHOT -> ESDEMediaConstants.FOLDER_SCREENSHOTS
                ContentType.TITLESCREEN -> ESDEMediaConstants.FOLDER_TITLESCREENS
                ContentType.BOX_ART -> ESDEMediaConstants.FOLDER_COVERS
                ContentType.MARQUEE -> ESDEMediaConstants.FOLDER_MARQUEES
                ContentType.LOGO -> ESDEMediaConstants.FOLDER_MARQUEES
                ContentType.MANUAL -> "manuals"
                else -> {
                    Log.w(TAG, "Unknown content type: ${image.imageType}, using screenshots folder")
                    ESDEMediaConstants.FOLDER_SCREENSHOTS
                }
            }
            
            // Create the target directory
            val targetDir = File(ESDEMediaConstants.ESDE_MEDIA_PATH, folderName)
            if (!targetDir.exists()) {
                targetDir.mkdirs()
            }
            
            // Extract file extension from URL
            val fileExtension = extractFileExtension(image.url)
            
            // Create filename: gameId + type + extension
            val fileName = "${image.gameId}_${image.imageType.name.lowercase()}$fileExtension"
            val targetFile = File(targetDir, fileName)
            
            // Download the image
            val request = Request.Builder()
                .url(image.url)
                .build()
            
            httpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Failed to download image: HTTP ${response.code}")
                }
                
                val body = response.body ?: throw IOException("Empty response body")
                
                // Save to file
                FileOutputStream(targetFile).use { outputStream ->
                    body.byteStream().use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                
                Log.d(TAG, "Successfully downloaded image to: ${targetFile.absolutePath}")
            }
            
        } catch (e: IOException) {
            Log.e(TAG, "Failed to download image: ${image.url}", e)
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error downloading image: ${image.url}", e)
            throw e
        }
    }
    
    /**
     * Extracts the file extension from a URL, defaulting to .jpg if not found.
     */
    private fun extractFileExtension(url: String): String {
        val fileName = url.substringAfterLast('/').substringBefore('?')
        val extension = fileName.substringAfterLast('.', "")
        
        return when {
            extension.isNotEmpty() && extension in ESDEMediaConstants.IMAGE_EXTENSIONS -> ".$extension"
            extension.equals("svg", ignoreCase = true) -> ".svg"
            else -> ".jpg" // Default extension
        }
    }
}
