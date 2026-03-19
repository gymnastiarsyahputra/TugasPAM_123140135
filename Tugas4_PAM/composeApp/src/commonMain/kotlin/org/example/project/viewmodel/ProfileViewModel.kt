package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.ProfileUiState

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun toggleDarkMode(isDark: Boolean) {
        _uiState.update { it.copy(isDarkMode = isDark) }
    }

    // Fungsi untuk membuka/menutup mode edit
    fun setEditing(isEditing: Boolean) {
        _uiState.update { currentState ->
            if (isEditing) {
                // Saat tombol edit ditekan, salin data asli ke draft
                currentState.copy(isEditing = true, draftName = currentState.name, draftBio = currentState.bio)
            } else {
                currentState.copy(isEditing = false)
            }
        }
    }

    fun updateDraftName(newName: String) {
        _uiState.update { it.copy(draftName = newName) }
    }

    fun updateDraftBio(newBio: String) {
        _uiState.update { it.copy(draftBio = newBio) }
    }

    // Fungsi untuk menyimpan perubahan ke State utama
    fun saveProfile() {
        _uiState.update { currentState ->
            currentState.copy(
                name = currentState.draftName,
                bio = currentState.draftBio,
                isEditing = false
            )
        }
    }
}