# 📝 Notes App - Kotlin Multiplatform (Tugas 7 PAM)

Aplikasi catatan sederhana yang dibangun menggunakan **Kotlin Multiplatform (KMP)**. Aplikasi ini mendemonstrasikan implementasi penyimpanan data lokal menggunakan database SQL serta manajemen preferensi pengguna (Settings).

## 🚀 Fitur Utama

- **CRUD Operations**: Menambah dan menghapus catatan secara permanen ke database lokal.
- **Real-time Search**: Mencari catatan berdasarkan judul atau isi secara instan melalui fitur pencarian di layar utama.
- **Offline Persistence**: Data tersimpan aman di SQLite, sehingga catatan tidak hilang saat aplikasi ditutup.
- **Dark/Light Mode**: Pengaturan tema aplikasi yang tersimpan secara lokal menggunakan *Multiplatform Settings* (DataStore).
- **Clean Architecture**: Menggunakan pola MVVM (Model-View-ViewModel) untuk memisahkan logika bisnis dengan tampilan UI.

## 🛠️ Tech Stack

- **UI Framework**: [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- **Database**: [SQLDelight](https://cashapp.github.io/sqldelight/) (SQLite for Multiplatform)
- **Local Storage**: [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings)
- **Navigation**: [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- **Language**: Kotlin

## 📂 Struktur Proyek

Proyek ini mengikuti struktur folder standar Kotlin Multiplatform:

```text
composeApp/
├── src/
│   ├── commonMain/           # Logika Utama (Shareable)
│   │   ├── kotlin/
│   │   │   └── org.example.project/
│   │   │       ├── data/         # Repository, Database Driver, Settings
│   │   │       ├── screens/      # UI Compose (Notes, Add, Settings)
│   │   │       ├── viewmodel/    # Logika Bisnis (NotesViewModel)
│   │   │       └── App.kt        # Entry point UI & Navigasi
│   │   └── sqldelight/       # Skema database (.sq)
│   ├── androidMain/          # Implementasi Spesifik Android
│   │   └── kotlin/
│   │       └── org.example.project/
│   │           ├── MainActivity.kt
│   │           └── DatabaseDriverFactory.kt
│   └── jvmMain/              # Implementasi Spesifik Desktop/JVM
│       └── kotlin/
│           └── org.example.project/
│               ├── main.kt
│               └── DatabaseDriverFactory.kt