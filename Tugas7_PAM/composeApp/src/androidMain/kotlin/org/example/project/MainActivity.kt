package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.russhwolf.settings.SharedPreferencesSettings
import org.example.project.data.DatabaseDriverFactory
import org.example.project.data.SettingsManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Membuat Supir Database
        val driverFactory = DatabaseDriverFactory(this)

        // 2. Membuat Mesin Settings (DataStore) khusus Android
        val sharedPrefs = getSharedPreferences("notes_settings", MODE_PRIVATE)
        val observableSettings = SharedPreferencesSettings(sharedPrefs)
        val settingsManager = SettingsManager(observableSettings)

        setContent {
            // 3. Mengirim kedua mesin tersebut ke UI Utama KMP
            App(driverFactory, settingsManager)

        }
    }
}