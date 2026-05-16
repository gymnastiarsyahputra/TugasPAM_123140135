package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.db.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.data.NoteRepository

class NotesViewModel(
    private val repository: org.example.project.data.NoteRepository,
    private val aiRepository: org.example.project.data.AIRepository
) : ViewModel() {

    // --- STATE UNTUK AI SUMMARY ---
    private val _summaryResult = MutableStateFlow<String?>(null)
    val summaryResult = _summaryResult.asStateFlow()

    private val _isSummarizing = MutableStateFlow(false)
    val isSummarizing = _isSummarizing.asStateFlow()

    // 1. Mengambil data dari database secara real-time (Flow)
    val notes: StateFlow<List<Note>> = repository.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 2. State untuk fitur Search
    private val _searchResults = MutableStateFlow<List<Note>>(emptyList())
    val searchResults: StateFlow<List<Note>> = _searchResults.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // 3. Fungsi CRUD
    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(title, content)
        }
    }

    // Fungsi eksekusi rangkuman
    fun summarizeNote(content: String) {
        viewModelScope.launch {
            _isSummarizing.value = true
            _summaryResult.value = null // Kosongkan hasil yang lama

            // Memanggil AI dari repository
            aiRepository.summarize(content).onSuccess { result ->
                _summaryResult.value = result
            }.onFailure { error ->
                _summaryResult.value = "Gagal merangkum: ${error.message}"
            }

            _isSummarizing.value = false
        }
    }

    fun clearSummary() {
        _summaryResult.value = null
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch {
            repository.updateNote(id, title, content)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    // 4. Fungsi Pencarian (Search)
    fun searchNotes(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            repository.searchNotes(query).collect { results ->
                _searchResults.value = results
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
    }
}