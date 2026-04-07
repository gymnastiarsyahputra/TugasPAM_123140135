package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.navigation.MainNavigation
import org.example.project.viewmodel.NotesViewModel

@Composable
fun App() {
    MaterialTheme {
        val notesViewModel: NotesViewModel = viewModel { NotesViewModel() }
        MainNavigation(viewModel = notesViewModel)
    }
}