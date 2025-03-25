package no.uio.ifi.in2000.sondrein.ai_chat.data.repository

import no.uio.ifi.in2000.sondrein.ai_chat.data.datasource.GeminiDatasource
import no.uio.ifi.in2000.sondrein.ai_chat.data.models.GeminiResponse

class GeminiRepository() {
    private val datasource = GeminiDatasource()

    suspend fun getAnswer(question: String): GeminiResponse {
        return datasource.askQuestion(question)
    }
}
