package org.example.project.data

// Blueprint fitur AI kita
interface AIRepository {
    suspend fun summarize(text: String): Result<String>
}

// Implementasi nyata dari blueprint di atas
class AIRepositoryImpl(private val geminiService: GeminiService) : AIRepository {
    override suspend fun summarize(text: String): Result<String> {
        // Prompt Engineering untuk AI (sangat spesifik agar hasilnya bagus)
        val prompt = """
            Tolong rangkum isi catatan berikut dalam 1-2 kalimat singkat saja.
            Fokus pada inti pesannya.
            
            Teks catatan:
            $text
        """.trimIndent()

        return geminiService.generateContent(prompt)
    }
}