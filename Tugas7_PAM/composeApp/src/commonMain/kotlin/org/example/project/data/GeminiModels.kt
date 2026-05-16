package org.example.project.data

import kotlinx.serialization.Serializable

// Model untuk mengirim pertanyaan (Request)
@Serializable
data class GeminiRequest(
    val contents: List<Content>
)

@Serializable
data class Content(
    val parts: List<Part>,
    val role: String = "user"
)

@Serializable
data class Part(
    val text: String
)

// Model untuk menerima jawaban (Response)
@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>
)

@Serializable
data class Candidate(
    val content: Content,
    val finishReason: String? = null
)