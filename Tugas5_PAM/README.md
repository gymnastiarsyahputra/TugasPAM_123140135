# Tugas Praktikum 5 PAM - Notes App Navigation

Repositori ini berisi implementasi Tugas Praktikum Pertemuan 5 mata kuliah Pengembangan Aplikasi Mobile (PAM), Program Studi Teknik Informatika, Institut Teknologi Sumatera (ITERA). Proyek ini berfokus pada implementasi navigasi multi-layar menggunakan Jetpack Navigation di Compose Multiplatform.

## 🌟 Fitur dan Pemenuhan Rubrik
1. **Bottom Navigation (25%)**: Terdapat 3 tab fungsional (Notes, Favorites, Profile) menggunakan `NavigationBar`.
2. **Navigation with Args (25%)**: Meneruskan argumen `noteId` dari Note List ke halaman Note Detail dan Edit Note secara dinamis.
3. **Navigation Flow (20%)**: Mendukung navigasi maju dan mundur (*back stack*) dengan rapi menggunakan `popBackStack()` serta manajemen state tab menggunakan `popUpTo` dan `launchSingleTop`.
4. **Code Structure (20%)**: Menerapkan arsitektur yang rapi dengan pemisahan *package* (`data`, `navigation`, `screens`, `viewmodel`).
5. **Bonus (+10%)**: Implementasi *Side Menu* menggunakan `ModalNavigationDrawer` yang tersinkronisasi dengan rute aplikasi.


