package no.uio.ifi.in2000.sondrein.ai_chat.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.sondrein.ai_chat.data.datasource.GeminiDatasource
import no.uio.ifi.in2000.sondrein.ai_chat.data.models.GeminiResponse
import no.uio.ifi.in2000.sondrein.ai_chat.data.repository.GeminiRepository

class HomeViewModel : ViewModel() {
    private val repository = GeminiRepository(GeminiDatasource())

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun askQuestion(question: String) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            when (val response = repository.getAnswer(question)) {
                is GeminiResponse.Success -> {
                    _uiState.value = UiState(response = response.text, isLoading = false)  // Oppdaterer med svar
                }
                is GeminiResponse.Error -> {
                    _uiState.value = UiState(response = "Error: ${response.message}", isLoading = false)  // Håndterer feilmelding
                }
            }
        }
    }
}

data class UiState(
    val response: String = "",
    val isLoading: Boolean = false
)