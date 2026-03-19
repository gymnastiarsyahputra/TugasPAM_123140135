package org.example.project.data

data class ProfileUiState(
    val name: String = "Gymnastiar",
    val bio: String = "Mahasiswa Teknik Informatika ITERA",
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false,

    // State sementara saat form edit diketik
    val draftName: String = "",
    val draftBio: String = ""
)