package org.example.project.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notes.db.Note
import org.example.project.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    onNavigateToAdd: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val notes by viewModel.notes.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // State AI
    val isSummarizing by viewModel.isSummarizing.collectAsState()
    val summaryResult by viewModel.summaryResult.collectAsState()

    val displayNotes = if (searchQuery.isNotEmpty()) searchResults else notes

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Notes") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            // Indikator Jaringan (Tugas 8)
            NetworkStatusIndicator()

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.searchNotes(it) },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                label = { Text("Search Notes...") }
            )

            if (displayNotes.isEmpty()) {
                Text("Belum ada catatan.", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(displayNotes) { note ->
                        NoteItem(
                            note = note,
                            onDelete = { viewModel.deleteNote(note.id) },
                            onSummarize = { viewModel.summarizeNote(note.content) }
                        )
                    }
                }
            }
        }

        // Pop-up Loading AI
        if (isSummarizing) {
            AlertDialog(
                onDismissRequest = { },
                confirmButton = {},
                title = { Text("✨ AI sedang bekerja") },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Membaca dan merangkum catatanmu...")
                    }
                }
            )
        }

        // Pop-up Hasil Rangkuman AI
        summaryResult?.let { summary ->
            AlertDialog(
                onDismissRequest = { viewModel.clearSummary() },
                confirmButton = {
                    TextButton(onClick = { viewModel.clearSummary() }) {
                        Text("Tutup")
                    }
                },
                title = { Text("✨ Rangkuman Catatan") },
                text = { Text(summary) }
            )
        }
    }
}

@Composable
fun NoteItem(note: Note, onDelete: () -> Unit, onSummarize: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
            }

            Row {
                IconButton(onClick = onSummarize) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Summary",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun NetworkStatusIndicator() {
    val networkMonitor: org.example.project.NetworkMonitor = org.koin.compose.koinInject()
    val isConnected by networkMonitor.observeConnectivity().collectAsState(initial = true)

    androidx.compose.animation.AnimatedVisibility(visible = !isConnected) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.errorContainer
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CloudOff,
                    contentDescription = "Offline",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "⚠️ Tidak Ada Koneksi Internet",
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}