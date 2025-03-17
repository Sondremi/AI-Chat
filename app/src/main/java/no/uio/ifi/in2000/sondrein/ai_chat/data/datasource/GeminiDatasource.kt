package no.uio.ifi.in2000.sondrein.ai_chat.data.datasource

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.TextPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import no.uio.ifi.in2000.sondrein.ai_chat.BuildConfig
import no.uio.ifi.in2000.sondrein.ai_chat.data.models.GeminiResponse

class GeminiDatasource {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    suspend fun askQuestion(question: String): GeminiResponse {
        return try {
            val response: GenerateContentResponse = withContext(Dispatchers.IO) {
                generativeModel.generateContent(question)
            }

            val textPart = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()
            if (textPart is TextPart) {
                GeminiResponse.Success(textPart.text)
            } else {
                GeminiResponse.Error("Response was not in expected text format")
            }
        } catch (e: Exception) {
            GeminiResponse.Error("Error: ${e.message}")
        }
    }
}
