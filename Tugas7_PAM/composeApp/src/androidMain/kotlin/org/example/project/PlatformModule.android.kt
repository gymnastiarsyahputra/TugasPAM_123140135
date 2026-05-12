package org.example.project

import android.content.Context
import org.koin.dsl.module
import org.example.project.data.DatabaseDriverFactory
import org.example.project.data.SettingsManager
import com.russhwolf.settings.SharedPreferencesSettings

actual val platformModule = module {
    // Platform APIs (get() akan otomatis mengambil Context dari Android)
    single { DeviceInfo() }
    single { NetworkMonitor(get()) }
    single { BatteryInfo(get()) }

    // Supir Database dari Tugas 7
    single { DatabaseDriverFactory(get()) }

    // Settings Manager dari Tugas 7
    single {
        val context: Context = get()
        val sharedPrefs = context.getSharedPreferences("notes_settings", Context.MODE_PRIVATE)
        SettingsManager(SharedPreferencesSettings(sharedPrefs))
    }
}