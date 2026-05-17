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
    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta"
    private val model = "gemini-2.0-flash"

    suspend fun generateContent(prompt: String): Result<String> = runCatching {
        val request = GeminiRequest(contents = listOf(Content(parts = listOf(Part(text = prompt)))))
        val response: GeminiResponse = client.post("$baseUrl/models/$model:generateContent") {
            contentType(ContentType.Application.Json)
            parameter("key", ApiConfig.geminiApiKey)
            setBody(request)
        }.body()
        response.candidates.first().content.parts.first().text
    }
}