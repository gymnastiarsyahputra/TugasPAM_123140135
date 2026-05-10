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
fun App(driverFactory: DatabaseDriverFactory, settingsManager: SettingsManager) {
    // Membaca pengaturan tema dari DataStore
    val theme by settingsManager.themeFlow.collectAsState(initial = "light")

    MaterialTheme(
        colorScheme = if (theme == "dark") darkColorScheme() else lightColorScheme()
    ) {
        val database = remember { NotesDatabase(driverFactory.createDriver()) }
        val repository = remember { NoteRepository(database) }
        val viewModel = remember { NotesViewModel(repository) }
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