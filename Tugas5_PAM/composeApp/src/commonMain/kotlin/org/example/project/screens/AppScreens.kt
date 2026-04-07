package org.example.project.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.viewmodel.NotesViewModel
import org.example.project.data.Note

// --- 1. NOTES SCREEN (List + FAB) ---
@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToAdd: () -> Unit
) {
    val notes by viewModel.notes.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onNavigateToDetail(note.id) } // Navigasi ke Detail bawa ID
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(note.title, style = MaterialTheme.typography.titleLarge)
                        Text(note.content, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

// --- 2. NOTE DETAIL SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    noteId: Int,
    viewModel: NotesViewModel,
    onNavigateToEdit: (Int) -> Unit,
    onBack: () -> Unit
) {
    val note = viewModel.getNoteById(noteId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Note") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            if (note != null) {
                Text(note.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(16.dp))
                Text(note.content, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(32.dp))
                Button(onClick = { onNavigateToEdit(noteId) }) {
                    Text("Edit Note")
                }
            } else {
                Text("Catatan tidak ditemukan")
            }
        }
    }
}

// --- 3. ADD & EDIT SCREEN (Contoh Sederhana) ---
@Composable
fun AddNoteScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text("Halaman Tambah Catatan", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Kembali (Batal)") }
    }
}

@Composable
fun EditNoteScreen(noteId: Int, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text("Halaman Edit Catatan ID: $noteId", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Simpan & Kembali") }
    }
}

// --- 4. FAVORITES & PROFILE SCREEN (Tab Lain) ---
@Composable
fun FavoritesScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("Halaman Favorites", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text("Halaman Profile (Tugas M3 & M4)", style = MaterialTheme.typography.headlineMedium)
    }
}