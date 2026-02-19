package org.example.project

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

// Implementasi actual untuk fungsi string tadi
actual fun getPlatformName(): String = "Android"