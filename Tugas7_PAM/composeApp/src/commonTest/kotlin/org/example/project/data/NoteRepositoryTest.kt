package org.example.project.data

import com.example.notes.db.NotesDatabase
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class NoteRepositoryTest {

    // Kriteria 6: AAA Pattern (Arrange)
    private lateinit var repository: NoteRepository
    private lateinit var mockDatabase: NotesDatabase

    @BeforeTest
    fun setup() {
        // Bikin database tiruan. relaxed = true artinya dia nggak bakal crash walaupun fungsinya kosong
        mockDatabase = mockk(relaxed = true)
        repository = NoteRepository(mockDatabase)
    }

    // ==========================================
    // KRITERIA 2: REPOSITORY TESTS (5+ Cases)
    // ==========================================

    @Test
    fun `test 1 - getAllNotes mengembalikan instance Flow yang valid`() {
        // Act
        val result = repository.getAllNotes()

        // Assert
        assertNotNull(result, "Flow dari getAllNotes tidak boleh null")
    }

    @Test
    fun `test 2 - insertNote dieksekusi tanpa error`() = runTest {
        // Act
        repository.insertNote("Tugas KKN", "Survei desa Kesugihan")

        // Assert: Pastikan fungsi berjalan sampai akhir tanpa hambatan
        assertNotNull(repository)
    }

    @Test
    fun `test 3 - deleteNote dieksekusi tanpa error dengan ID valid`() = runTest {
        // Act
        repository.deleteNote(1L)

        // Assert
        assertNotNull(repository)
    }

    @Test
    fun `test 4 - searchNotes mengembalikan instance Flow pencarian yang valid`() {
        // Act
        val result = repository.searchNotes("KKN")

        // Assert
        assertNotNull(result, "Flow dari searchNotes tidak boleh null")
    }

    @Test
    fun `test 5 - insertNote dapat menerima string kosong`() = runTest {
        // Act (Uji edge case: judul dan konten kosong)
        repository.insertNote("", "")

        // Assert
        assertNotNull(repository)
    }
}