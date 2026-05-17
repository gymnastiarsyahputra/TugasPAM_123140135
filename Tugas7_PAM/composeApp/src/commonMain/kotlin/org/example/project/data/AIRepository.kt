package org.example.project.data

interface AIRepository {
    suspend fun summarize(text: String): Result<String>
}

class AIRepositoryImpl(private val geminiService: GeminiService) : AIRepository {
    override suspend fun summarize(text: String): Result<String> {
        val prompt = """
            Tolong rangkum isi catatan berikut dalam 1-2 kalimat singkat saja.
            Fokus pada inti pesannya.
            
            Teks catatan:
            $text
        """.trimIndent()

        return geminiService.generateContent(prompt)
    }
}