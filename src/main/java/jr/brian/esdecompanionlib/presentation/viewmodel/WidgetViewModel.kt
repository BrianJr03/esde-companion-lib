package jr.brian.esdecompanionlib.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jr.brian.esdecompanionlib.data.repository.WidgetRepository
import javax.inject.Inject

@HiltViewModel
class WidgetViewModel @Inject constructor(
    private val widgetRepository: WidgetRepository
) : ViewModel() {
}
