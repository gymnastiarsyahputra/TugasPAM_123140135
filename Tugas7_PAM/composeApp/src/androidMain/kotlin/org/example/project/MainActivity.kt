package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Menyalakan Koin khusus untuk platform Android
            KoinApplication(application = {
                androidContext(applicationContext)
                modules(allModules + platformModule)
            }) {
                // UI Utama dipanggil tanpa perlu parameter apa-apa lagi!
                App()
            }
        }
    }
}