package org.example.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mengubah teks menjadi nama
            Text("Halo, M. Gymnastiar Syahputra!")

            // Menambahkan NIM
            Text("NIM: 123140135")

            // Menampilkan nama platform menggunakan bawaan template
            Text("Platform: ${getPlatform().name}")
        }
    }
}