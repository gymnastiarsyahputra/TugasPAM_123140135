package org.example.project

import org.koin.core.module.Module
import org.koin.dsl.module
import com.example.notes.db.NotesDatabase
import org.example.project.data.NoteRepository
import org.example.project.viewmodel.NotesViewModel
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

// Deklarasi bahwa setiap platform akan punya modul tambahannya sendiri
expect val platformModule: Module

// Modul utama yang dipakai bersama
val commonModule = module {
    // 1. Membuat Database (kode lu yang lama)
    single { NotesDatabase(get<org.example.project.data.DatabaseDriverFactory>().createDriver()) }

    // 2. Membuat Repository (kode lu yang lama)
    single { NoteRepository(get()) }

    // 1. Daftarkan AIRepository
    single<org.example.project.data.AIRepository> { org.example.project.data.AIRepositoryImpl(get()) }

    // 2. UBAH baris factory NotesViewModel lu yang lama menjadi seperti ini:
    factory { NotesViewModel(repository = get(), aiRepository = get()) }

    // 1. Membuat Mesin Pencari Internet (Ktor HttpClient) yang paham format JSON
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    single { org.example.project.data.GeminiService(get()) }
}