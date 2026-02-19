package org.example.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

// Hapus
expect fun getPlatformName(): String