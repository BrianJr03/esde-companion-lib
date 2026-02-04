package jr.brian.esdecompanionlib.data.model

enum class WidgetContext {
    GAME,
    SYSTEM
}

enum class MediaSlot(val index: Int) {
    SLOT_1(0),
    SLOT_2(1),
    SLOT_3(2),
    SLOT_4(3)
}

data class WidgetState(
    val id: String,
    val contentType: ContentType,
    val mediaSlot: MediaSlot,
    val contentUri: String? = null,
    val isLoading: Boolean = false,
    val hasAudio: Boolean = false,
    val isVisible: Boolean = true,
    val title: String? = null
)

data class WidgetPageState(
    val currentPage: Int = 0,
    val totalPages: Int = 1,
    val widgets: List<WidgetState> = emptyList(),
    val context: WidgetContext = WidgetContext.SYSTEM
)
