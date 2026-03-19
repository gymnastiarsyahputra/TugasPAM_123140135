package org.example.project.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    // Observe state dari ViewModel [cite: 1024]
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Switch Dark Mode [cite: 1206]
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = uiState.isDarkMode,
                onCheckedChange = { viewModel.toggleDarkMode(it) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (uiState.isEditing) {
            // Tampilan Form Edit [cite: 1202]
            StatelessTextField(
                label = "Nama",
                value = uiState.draftName,
                onValueChange = { viewModel.updateDraftName(it) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            StatelessTextField(
                label = "Bio",
                value = uiState.draftBio,
                onValueChange = { viewModel.updateDraftBio(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                OutlinedButton(onClick = { viewModel.setEditing(false) }) {
                    Text("Batal")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { viewModel.saveProfile() }) {
                    Text("Simpan") // Save button yang update ViewModel [cite: 1204]
                }
            }
        } else {
            // Tampilan Profil Normal
            Text(text = uiState.name, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = uiState.bio, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { viewModel.setEditing(true) }) {
                Text("Edit Profil")
            }
        }
    }
}

// Komponen Reusable dengan State Hoisting [cite: 1095, 1096, 1097, 1098, 1099]
@Composable
fun StatelessTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}