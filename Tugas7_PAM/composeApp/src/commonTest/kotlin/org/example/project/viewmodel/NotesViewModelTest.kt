package org.example.project.viewmodel

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.example.project.data.AIRepository
import org.example.project.data.NoteRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {

    // Kriteria 6: AAA Pattern (Arrange - Persiapan)
    private lateinit var viewModel: NotesViewModel
    private lateinit var mockRepository: NoteRepository
    private lateinit var mockAiRepository: AIRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set Main thread untuk testing UI/ViewModel

        // Bikin "Objek Palsu" (MockK) sesuai rubrik
        mockRepository = mockk(relaxed = true)
        mockAiRepository = mockk(relaxed = true)

        // Bikin skenario default kalau repository dipanggil
        every { mockRepository.getAllNotes() } returns flowOf(emptyList())

        viewModel = NotesViewModel(mockRepository, mockAiRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ==========================================
    // KRITERIA 3: VIEWMODEL TESTS (MockK) - 4+ Cases
    // ==========================================

    @Test
    fun `test addNote memanggil fungsi insert di repository`() = runTest {
        // Act (Tindakan)
        viewModel.addNote("Tugas PAM", "Selesaikan unit test")
        advanceUntilIdle()

        // Assert (Evaluasi dengan MockK)
        coVerify(exactly = 1) { mockRepository.insertNote("Tugas PAM", "Selesaikan unit test") }
    }

    @Test
    fun `test deleteNote memanggil fungsi delete di repository`() = runTest {
        viewModel.deleteNote(10L)
        advanceUntilIdle()
        coVerify(exactly = 1) { mockRepository.deleteNote(10L) }
    }

    @Test
    fun `test clearSummary menghapus hasil rangkuman AI`() = runTest {
        viewModel.clearSummary()
        assertEquals(null, viewModel.summaryResult.value)
    }

    // ==========================================
    // KRITERIA 4: FLOW TESTS (Turbine) - 2+ Cases
    // ==========================================

    @Test
    fun `test searchNotes memperbarui aliran StateFlow pencarian`() = runTest {
        // Menguji Flow menggunakan library Turbine (.test)
        viewModel.searchQuery.test {
            assertEquals("", awaitItem()) // Cek state awal

            viewModel.searchNotes("Ujian")
            assertEquals("Ujian", awaitItem()) // Cek state setelah diupdate

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test summarizeNote mengubah state loading dan hasil AI`() = runTest {
        // Arrange (Persiapan skenario AI berhasil)
        coEvery { mockAiRepository.summarize(any()) } returns Result.success("Ini rangkuman AI")

        // Act & Assert (Uji aliran data dengan Turbine)
        viewModel.summaryResult.test {
            assertEquals(null, awaitItem()) // Awalnya kosong

            viewModel.summarizeNote("Teks yang sangat panjang")
            advanceUntilIdle()

            assertEquals("Ini rangkuman AI", awaitItem()) // Harus muncul hasil dari AI
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test isSummarizing memancarkan nilai true lalu false`() = runTest {
        coEvery { mockAiRepository.summarize(any()) } returns Result.success("Selesai")

        viewModel.isSummarizing.test {
            assertEquals(false, awaitItem()) // Awal

            viewModel.summarizeNote("Test loading")

            assertEquals(true, awaitItem())  // Saat proses loading
            assertEquals(false, awaitItem()) // Saat selesai
            cancelAndIgnoreRemainingEvents()
        }
    }
}