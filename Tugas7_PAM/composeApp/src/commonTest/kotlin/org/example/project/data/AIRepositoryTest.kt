package org.example.project.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AIRepositoryTest {

    // 1. Kita bikin "Gemini Palsu" (Mock)
    private val mockGeminiService = mockk<GeminiService>()

    // 2. Kita masukkan Gemini Palsu itu ke dalam Repository yang mau dites
    private val aiRepository = AIRepositoryImpl(mockGeminiService)

    @Test
    fun `test summarize berhasil mengembalikan teks rangkuman`() = runTest {
        // PERSIAPAN: Kalau Gemini Palsu dipanggil, paksa dia buat ngejawab "Ini hasil rangkuman."
        coEvery { mockGeminiService.generateContent(any()) } returns Result.success("Ini hasil rangkuman.")

        // AKSI: Kita coba panggil fungsi summarize dari repository
        val result = aiRepository.summarize("Teks catatan yang sangat panjang dan butuh dirangkum...")

        // EVALUASI: Cek apakah hasilnya beneran sukses dan jawabannya sesuai tebakan
        assertTrue(result.isSuccess, "Hasil harusnya sukses")
        assertEquals("Ini hasil rangkuman.", result.getOrNull(), "Teks rangkuman tidak sesuai")
    }
}