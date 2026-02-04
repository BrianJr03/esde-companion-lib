package jr.brian.esdecompanionlib.data.repository

import jr.brian.esdecompanionlib.data.database.dao.LoudnessDao
import jr.brian.esdecompanionlib.data.model.AudioState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioRepository @Inject constructor(
    private val loudnessDao: LoudnessDao
) {
    private val _audioState = MutableStateFlow(AudioState())
    val audioState: StateFlow<AudioState> = _audioState.asStateFlow()

    fun updateBackgroundState(isActive: Boolean) {
        _audioState.value = _audioState.value.copy(isBackgroundActive = isActive)
    }
}
