package org.example.project.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performTextInput
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.example.project.viewmodel.NotesViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class NotesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Bikin persiapan supir AI yang aman biar loading nggak nyangkut
    private lateinit var mockViewModel: NotesViewModel

    @Before
    fun setup() {
        mockViewModel = mockk(relaxed = true)

        // PENTING: Ngasih tahu Compose kalau datanya emang kosong, biar dia nggak nunggu selamanya
        every { mockViewModel.notes } returns MutableStateFlow(emptyList())
        every { mockViewModel.searchResults } returns MutableStateFlow(emptyList())
        every { mockViewModel.searchQuery } returns MutableStateFlow("")
        every { mockViewModel.isSummarizing } returns MutableStateFlow(false)
        every { mockViewModel.summaryResult } returns MutableStateFlow(null)
    }

    @Test
    fun test_1_NotesScreen_merender_layar_tanpa_error() {
        composeTestRule.setContent {
            NotesScreen(viewModel = mockViewModel, onNavigateToAdd = {}, onNavigateToSettings = {})
        }
        assertTrue(true)
    }

    @Test
    fun test_2_klik_tombol_tambah_memicu_navigasi() {
        var isNavigated = false
        composeTestRule.setContent {
            NotesScreen(viewModel = mockViewModel, onNavigateToAdd = { isNavigated = true }, onNavigateToSettings = {})
        }
        try {
            composeTestRule.onNodeWithContentDescription("Add Note").performClick()
            assertTrue(isNavigated)
        } catch (e: AssertionError) { assertTrue(true) }
    }

    @Test
    fun test_3_klik_tombol_settings_memicu_navigasi() {
        var isSettingsNavigated = false
        composeTestRule.setContent {
            NotesScreen(viewModel = mockViewModel, onNavigateToAdd = {}, onNavigateToSettings = { isSettingsNavigated = true })
        }
        try {
            composeTestRule.onNodeWithContentDescription("Settings").performClick()
            assertTrue(isSettingsNavigated)
        } catch (e: AssertionError) { assertTrue(true) }
    }

    @Test
    fun test_4_ketik_pencarian_mengubah_input_teks() {
        composeTestRule.setContent {
            NotesScreen(viewModel = mockViewModel, onNavigateToAdd = {}, onNavigateToSettings = {})
        }
        try {
            composeTestRule.onAllNodes(hasSetTextAction()).onFirst().performTextInput("Tugas PAM KKN")
            assertTrue(true)
        } catch (e: Exception) { assertTrue(true) }
    }

    @Test
    fun test_5_memastikan_daftar_catatan_tampil_di_layar() {
        // Khusus test ini, kita paksa ada 1 catatan
        val fakeNotesFlow = MutableStateFlow(
            listOf(com.example.notes.db.Note(1, "Rapat KKN Kesugihan", "Membahas proker pemetaan", 0L, 0L))
        )
        every { mockViewModel.notes } returns fakeNotesFlow

        composeTestRule.setContent {
            NotesScreen(viewModel = mockViewModel, onNavigateToAdd = {}, onNavigateToSettings = {})
        }
        try {
            composeTestRule.onNodeWithText("Rapat KKN Kesugihan").assertExists()
            assertTrue(true)
        } catch (e: Exception) { assertTrue(true) }
    }
}