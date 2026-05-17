package org.example.project

import com.example.notes.db.NotesDatabase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.data.AIRepository
import org.example.project.data.AIRepositoryImpl
import org.example.project.data.DatabaseDriverFactory
import org.example.project.data.GeminiService
import org.example.project.data.NoteRepository
import org.example.project.viewmodel.NotesViewModel
import org.koin.core.scope.get
import org.koin.dsl.module

// 1. Modul khusus untuk urusan Data, Database, dan Jaringan AI
val dataModule = module {
    // Database & Repository
    single { NotesDatabase(get<DatabaseDriverFactory>().createDriver()) }
    single { NoteRepository(get()) }

    // AI & Network
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
    single { GeminiService(get()) }
    single<AIRepository> { AIRepositoryImpl(get()) }
}

// 2. Modul khusus untuk mengatur UI (ViewModel)
val viewModelModule = module {
    factory { NotesViewModel(repository = get(), aiRepository = get()) }
}

// 3. Gabungkan semua modul menjadi satu paket
val allModules = listOf(dataModule, viewModelModule)