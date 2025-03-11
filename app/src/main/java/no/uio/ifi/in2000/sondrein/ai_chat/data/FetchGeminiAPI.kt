package no.uio.ifi.in2000.sondrein.ai_chat.data

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.runBlocking
import com.google.ai.client.generativeai.type.TextPart
import android.content.Context
import no.uio.ifi.in2000.sondrein.ai_chat.BuildConfig

fun askQuestion(context: Context, question: String): String {
    val apiKey = BuildConfig.GEMINI_API_KEY

    // Initialize the Gemini model
    val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = apiKey
    )

    // Generate content and return the response
    return runBlocking {
        try {
            // Using the String overload directly to avoid ambiguity
            val response: GenerateContentResponse = withContext(Dispatchers.IO) {
                generativeModel.generateContent(question)
            }

            // Extract text from the response correctly
            val textPart = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()

            // Check if it's a TextPart and extract the actual text
            if (textPart is TextPart) {
                textPart.text
            } else {
                "Response was not in expected text format"
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}