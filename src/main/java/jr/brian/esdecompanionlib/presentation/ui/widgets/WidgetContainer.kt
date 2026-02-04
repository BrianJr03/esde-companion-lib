package jr.brian.esdecompanionlib.presentation.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jr.brian.esdecompanionlib.data.model.ContentType
import jr.brian.esdecompanionlib.data.model.WidgetState

@Composable
fun WidgetContainer(
    widgetState: WidgetState,
    modifier: Modifier = Modifier,
    audioEnabled: Boolean = false,
    onError: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            widgetState.isLoading -> {
                LoadingWidget(modifier = Modifier.fillMaxSize())
            }
            widgetState.contentUri == null -> {
                EmptyWidgetPlaceholder(
                    contentType = widgetState.contentType,
                    modifier = Modifier.fillMaxSize()
                )
            }
            else -> {
                when (widgetState.contentType) {
                    ContentType.VIDEO -> {
                        VideoWidget(
                            videoUri = widgetState.contentUri,
                            audioEnabled = audioEnabled && widgetState.hasAudio,
                            modifier = Modifier.fillMaxSize(),
                            onError = onError
                        )
                    }
                    ContentType.MUSIC -> {
                        AudioWidget(
                            audioUri = widgetState.contentUri,
                            volume = if (audioEnabled) 1f else 0f,
                            modifier = Modifier.fillMaxSize(),
                            onError = onError
                        )
                    }
                    ContentType.IMAGE,
                    ContentType.FANART,
                    ContentType.LOGO,
                    ContentType.MARQUEE,
                    ContentType.SCREENSHOT,
                    ContentType.TITLESCREEN,
                    ContentType.BOX_ART,
                    ContentType.MANUAL -> {
                        ImageWidget(
                            imageUri = widgetState.contentUri,
                            modifier = Modifier.fillMaxSize(),
                            onError = onError
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyWidgetPlaceholder(
    contentType: ContentType,
    modifier: Modifier = Modifier
) {
    when (contentType) {
        ContentType.VIDEO -> EmptyImageWidget(modifier)
        ContentType.MUSIC -> EmptyAudioWidget(modifier)
        else -> EmptyImageWidget(modifier)
    }
}
