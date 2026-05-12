package org.example.project

import org.koin.dsl.module
import org.example.project.data.DatabaseDriverFactory
import org.example.project.data.SettingsManager
import com.russhwolf.settings.PreferencesSettings
import java.util.prefs.Preferences

actual val platformModule = module {
    // Platform APIs
    single { DeviceInfo() }
    single { NetworkMonitor() }
    single { BatteryInfo() }

    // Supir Database
    single { DatabaseDriverFactory() }

    // Settings Manager
    single {
        val preferences = Preferences.userRoot()
        SettingsManager(PreferencesSettings(preferences))
    }
}