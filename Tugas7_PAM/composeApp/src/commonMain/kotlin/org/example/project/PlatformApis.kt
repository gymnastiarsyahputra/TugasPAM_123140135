package org.example.project

import kotlinx.coroutines.flow.Flow

// 1. Info Perangkat
expect class DeviceInfo {
    fun getDeviceName(): String
    fun getOsVersion(): String
}

// 2. Status Jaringan
expect class NetworkMonitor {
    fun isConnected(): Boolean
    fun observeConnectivity(): Flow<Boolean>
}

// 3. Status Baterai (Bonus 10%!)
expect class BatteryInfo {
    fun getBatteryLevel(): Int
    fun isCharging(): Boolean
}