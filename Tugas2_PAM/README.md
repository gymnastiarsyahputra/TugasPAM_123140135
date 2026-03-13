# Tugas 2 Praktikum PAM - News Feed Simulator

Proyek ini adalah simulasi *News Feed* sederhana yang dibangun menggunakan **Kotlin Coroutines** dan **Kotlin Flow** untuk memenuhi tugas Praktikum Pengembangan Aplikasi Mobile.

## Fitur yang Diimplementasikan
1. **Pembaruan Berkala**: Menggunakan `Flow` untuk memancarkan (emit) data berita baru setiap 2 detik.
2. **Penyaringan Data**: Menggunakan operator `.filter` untuk memilah berita berdasarkan kategori tertentu (Teknologi dan Kampus).
3. **Transformasi Data**: Menggunakan operator `.map` untuk mengubah format data sebelum ditampilkan.
4. **Manajemen State**: Menggunakan `StateFlow` untuk menyimpan dan memantau jumlah berita yang sudah dibaca secara reaktif.
5. **Operasi Asinkron**: Menggunakan *coroutines* (`async/await` dengan `Dispatchers.IO`) untuk mengambil detail berita tanpa memblokir aliran utama.
6. **Bonus - Error Handling**: Mengimplementasikan blok `.catch` pada Flow dan `try-catch` pada coroutines untuk mencegah *crash* saat terjadi kegagalan sistem.

## Cara Menjalankan Program
Karena proyek ini menggunakan struktur Kotlin Multiplatform (KMP) dan difokuskan pada pemrosesan logika *backend* (konsol), jalankan program melalui modul JVM:

1. Buka proyek ini di **Android Studio**.
2. Pada panel navigasi (Project View), buka *path* berikut:
   `composeApp > src > jvmMain > kotlin > org > example > project > ConsoleMain.kt`
   *(Catatan: Jika menggunakan tampilan mode "Android", folder `kotlin` mungkin akan berlabel `java`)*.
3. Buka file `ConsoleMain.kt`.
4. Klik ikon **Play (Segitiga Hijau)** di sebelah kiri baris `fun main()`.
5. Pilih **"Run 'ConsoleMainKt'"**.
6. Buka tab **Run** atau **Console** di bagian bawah Android Studio untuk melihat hasil keluaran (*output*) aliran berita.