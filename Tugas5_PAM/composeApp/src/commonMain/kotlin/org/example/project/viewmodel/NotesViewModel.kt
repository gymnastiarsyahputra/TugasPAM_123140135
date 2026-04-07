package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.Note

class NotesViewModel : ViewModel() {
    // Data dummy awal
    private val _notes = MutableStateFlow(
        listOf(
            Note(1, "Belajar Compose", "Mempelajari navigasi di Compose Multiplatform"),
            Note(2, "Ide Proyek", "Bikin aplikasi manajemen tugas harian")
        )
    )
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun getNoteById(id: Int): Note? {
        return _notes.value.find { it.id == id }
    }
}