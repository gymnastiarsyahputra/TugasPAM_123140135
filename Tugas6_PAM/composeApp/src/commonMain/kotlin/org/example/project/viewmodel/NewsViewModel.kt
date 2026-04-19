package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.NewsRepository
import org.example.project.data.Post

// Sealed class untuk UI State yang Proper
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _uiState = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Post>>> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            fetchData()
        }
    }

    fun refreshNews() {
        viewModelScope.launch {
            _isRefreshing.value = true
            fetchData()
            _isRefreshing.value = false
        }
    }

    private suspend fun fetchData() {
        repository.getNews()
            .onSuccess { posts ->
                _uiState.value = UiState.Success(posts)
            }
            .onFailure { error ->
                _uiState.value = UiState.Error(error.message ?: "Terjadi kesalahan jaringan")
            }
    }

    // Fungsi untuk mengambil 1 artikel secara synchronous dari state jika sudah sukses
    fun getPostFromState(id: Int): Post? {
        val currentState = _uiState.value
        if (currentState is UiState.Success) {
            return currentState.data.find { it.id == id }
        }
        return null
    }
}