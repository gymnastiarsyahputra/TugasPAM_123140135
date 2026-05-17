package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.db.NotesDatabase
import org.example.project.data.DatabaseDriverFactory
import org.example.project.data.NoteRepository
import org.example.project.data.SettingsManager
import org.example.project.screens.AddNoteScreen
import org.example.project.screens.NotesScreen
import org.example.project.screens.SettingsScreen
import org.example.project.viewmodel.NotesViewModel

@Composable
fun App() {
    // 1. Ambil mesinnya dari Koin secara otomatis (tanpa parameter!)
    val settingsManager: org.example.project.data.SettingsManager = org.koin.compose.koinInject()
    val viewModel: org.example.project.viewmodel.NotesViewModel = org.koin.compose.koinInject()

    // 2. Baca pengaturan tema menggunakan mesin yang sudah diambil
    val theme by settingsManager.themeFlow.collectAsState(initial = "light")

    // 3. Terapkan temanya ke MaterialTheme
    MaterialTheme(
        colorScheme = if (theme == "dark") darkColorScheme() else lightColorScheme()
    ) {
        // ... (Biarkan kode NavHost / UI kamu di bawah sini tetap sama seperti sebelumnya)
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                NotesScreen(
                    viewModel = viewModel,
                    onNavigateToAdd = { navController.navigate("add_note") },
                    onNavigateToSettings = { navController.navigate("settings") }
                )
            }
            composable("add_note") {
                AddNoteScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("settings") {
                SettingsScreen(
                    settingsManager = settingsManager,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}