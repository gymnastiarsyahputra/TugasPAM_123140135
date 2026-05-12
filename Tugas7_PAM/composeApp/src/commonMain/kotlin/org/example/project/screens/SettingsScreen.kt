package org.example.project.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.data.SettingsManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsManager: SettingsManager,
    onNavigateBack: () -> Unit
) { // <-- Kurung tutup parameter di sini

    // 1. Panggil mesin Koin-nya di DALAM badan fungsi
    val deviceInfo: org.example.project.DeviceInfo = org.koin.compose.koinInject()
    val batteryInfo: org.example.project.BatteryInfo = org.koin.compose.koinInject()

    // Ambil tema saat ini secara real-time
    val theme by settingsManager.themeFlow.collectAsState(initial = "system")
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("App Theme", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            // Baris untuk tombol tema
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = theme == "light",
                    onClick = { scope.launch { settingsManager.setTheme("light") } }
                )
                Text("Light")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = theme == "dark",
                    onClick = { scope.launch { settingsManager.setTheme("dark") } }
                )
                Text("Dark")
            } // <-- Row ditutup di sini

            // 2. Info perangkat ditaruh di bawah Row
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Informasi Perangkat (Bonus Tugas 8)",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "📱 Model HP: ${deviceInfo.getDeviceName()}")
                    Text(text = "⚙️ OS: ${deviceInfo.getOsVersion()}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "🔋 Sisa Baterai: ${batteryInfo.getBatteryLevel()}%")
                    Text(text = "⚡ Status: ${if (batteryInfo.isCharging()) "Sedang Dicas" else "Tidak Dicas"}")
                }
            }
        }
    }
}