package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.russhwolf.settings.PreferencesSettings
import org.example.project.data.SettingsManager
import java.util.prefs.Preferences

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PAM_Tugas5",
    ) {
        // 1. Memberikan "supir" database untuk versi Desktop (JVM)
        val driverFactory = DatabaseDriverFactory()

        // 2. Memberikan mesin penyimpanan (DataStore) untuk versi Desktop
        val preferences = Preferences.userRoot()
        val observableSettings = PreferencesSettings(preferences)
        val settingsManager = SettingsManager(observableSettings)

        // 3. Masukkan ke dalam App
        App(driverFactory, settingsManager)
    }
}