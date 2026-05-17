package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.db.Note
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.project.data.AIRepository
import org.example.project.data.NoteRepository

class NotesViewModel(
    private val repository: NoteRepository,
    private val aiRepository: AIRepository // <--- Ini yang dicari sama AppModule lu!
) : ViewModel() {

    val notes: StateFlow<List<Note>> = repository.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Note>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    // --- State AI ---
    private val _summaryResult = MutableStateFlow<String?>(null)
    val summaryResult = _summaryResult.asStateFlow()

    private val _isSummarizing = MutableStateFlow(false)
    val isSummarizing = _isSummarizing.asStateFlow()

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(title, content)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun searchNotes(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            repository.searchNotes(query).collect { results ->
                _searchResults.value = results
            }
        }
    }

    // --- Fungsi AI Summary ---
    fun summarizeNote(content: String) {
        viewModelScope.launch {
            _isSummarizing.value = true
            _summaryResult.value = null

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
}