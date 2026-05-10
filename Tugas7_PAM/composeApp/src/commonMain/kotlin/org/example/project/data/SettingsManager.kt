package org.example.project.data

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSettingsApi::class)
class SettingsManager(settings: ObservableSettings) {

    private val flowSettings: FlowSettings = settings.toFlowSettings()

    // Tema: "light", "dark", "system"
    val themeFlow: Flow<String> = flowSettings.getStringFlow("app_theme", "system")

    // Sort Order: "desc" (terbaru), "asc" (terlama)
    val sortOrderFlow: Flow<String> = flowSettings.getStringFlow("sort_order", "desc")

    suspend fun setTheme(theme: String) {
        flowSettings.putString("app_theme", theme)
    }

    suspend fun setSortOrder(order: String) {
        flowSettings.putString("sort_order", order)
    }
}