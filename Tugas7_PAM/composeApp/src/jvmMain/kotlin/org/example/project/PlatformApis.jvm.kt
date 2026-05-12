package org.example.project

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class DeviceInfo {
    actual fun getDeviceName(): String = "Desktop PC"
    actual fun getOsVersion(): String = System.getProperty("os.name")
}

actual class NetworkMonitor {
    actual fun isConnected(): Boolean = true
    actual fun observeConnectivity(): Flow<Boolean> = flowOf(true)
}

actual class BatteryInfo {
    actual fun getBatteryLevel(): Int = 100
    actual fun isCharging(): Boolean = true
}