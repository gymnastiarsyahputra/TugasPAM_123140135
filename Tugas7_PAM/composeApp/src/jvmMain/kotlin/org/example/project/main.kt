package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.compose.KoinApplication

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PAM_Tugas8",
    ) {
        // Menyalakan Koin khusus untuk Desktop (JVM)
        KoinApplication(application = {
            modules(commonModule, platformModule)
        }) {
            // Bersih tanpa parameter!
            App()
        }
    }
}