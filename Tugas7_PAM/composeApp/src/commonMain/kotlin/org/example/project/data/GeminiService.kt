package org.example.project.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.ApiConfig

class GeminiService(private val client: HttpClient) {
    // Endpoint resmi dari Google Gemini
    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta"
    private val model = "gemini-2.0-flash"

    // Fungsi untuk mengirim pesan dan menerima balasan
    suspend fun generateContent(prompt: String): Result<String> = runCatching {
        // 1. Bungkus pesan ke dalam amplop JSON yang udah kita buat
        val request = GeminiRequest(
            contents = listOf(
                Content(parts = listOf(Part(text = prompt)))
            )
        )

        // 2. Kirim ke kantor pos Google (HTTP POST)
        val response: GeminiResponse = client.post("$baseUrl/models/$model:generateContent") {
            contentType(ContentType.Application.Json)
            parameter("key", ApiConfig.geminiApiKey) // Kunci rahasia lu dimasukin di sini
            setBody(request)
        }.body()

        // 3. Ekstrak teks balasan dari AI
        response.candidates.first().content.parts.first().text
    }
}