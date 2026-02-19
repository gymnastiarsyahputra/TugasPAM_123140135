package org.example.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Modifikasi: Nama Anda [cite: 433]
            Text("Halo, [M. Gymnastiar Syahputra]!")

            // Modifikasi: NIM Anda [cite: 434]
            Text("NIM: [123140135]")

            // Modifikasi: Menampilkan nama platform [cite: 435]
            Text("Platform: ${getPlatformName()}")
        }
    }
}