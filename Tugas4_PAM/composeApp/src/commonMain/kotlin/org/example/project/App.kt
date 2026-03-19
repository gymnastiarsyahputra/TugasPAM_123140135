package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.ui.ProfileScreen
import org.example.project.viewmodel.ProfileViewModel

@Composable
fun App() {
    // Inisialisasi ViewModel [cite: 1022]
    val viewModel: ProfileViewModel = viewModel { ProfileViewModel() }
    val uiState by viewModel.uiState.collectAsState()

    // Logika Bonus: Mengubah skema warna berdasarkan isDarkMode
    val colors = if (uiState.isDarkMode) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    MaterialTheme(colorScheme = colors) {
        // Surface akan memberikan warna background otomatis menyesuaikan mode gelap/terang
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileScreen(viewModel = viewModel)
        }
    }
}