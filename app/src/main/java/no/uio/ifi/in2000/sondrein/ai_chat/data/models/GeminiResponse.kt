package no.uio.ifi.in2000.sondrein.ai_chat.data.models

sealed class GeminiResponse {
    data class Success(val text: String) : GeminiResponse()
    data class Error(val message: String) : GeminiResponse()
}
