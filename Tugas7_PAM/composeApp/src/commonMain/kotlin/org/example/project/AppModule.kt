package org.example.project

import org.koin.core.module.Module
import org.koin.dsl.module
import com.example.notes.db.NotesDatabase
import org.example.project.data.NoteRepository
import org.example.project.viewmodel.NotesViewModel
// Tambahkan import lain jika ada yang merah

// Deklarasi bahwa setiap platform akan punya modul tambahannya sendiri
expect val platformModule: Module

// Modul utama yang dipakai bersama
val commonModule = module {
    // 1. Membuat Database (mengambil supir dari platformModule secara otomatis)
    single { NotesDatabase(get<org.example.project.data.DatabaseDriverFactory>().createDriver()) }

    // 2. Membuat Repository
    single { NoteRepository(get()) }

    // 3. Membuat ViewModel
    factory { NotesViewModel(get()) }
}