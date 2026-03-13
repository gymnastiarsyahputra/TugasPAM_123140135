# Tugas Praktikum 3 PAM - My Profile App

Repositori ini berisi implementasi tugas praktikum pertemuan 3 mata kuliah Pengembangan Aplikasi Mobile (PAM), Program Studi Teknik Informatika, Institut Teknologi Sumatera (ITERA). Aplikasi ini dibangun menggunakan **Compose Multiplatform** dengan target sistem Android dan Desktop.

## 📱 Deskripsi Aplikasi
"My Profile App" adalah aplikasi antarmuka pengguna (UI) yang menampilkan halaman profil interaktif. Proyek ini mendemonstrasikan pemahaman mengenai:
- Paradigma UI Deklaratif menggunakan Kotlin.
- Implementasi *Basic Layouts* (`Column`, `Row`, `Box`).
- Penggunaan komponen UI standar seperti `Text`, `Button`, `Card`, dan `Icon`.
- Penataan elemen menggunakan *Modifier chain*.
- Pembuatan *Reusable Composable Functions* (`ProfileHeader`, `ProfileCard`, `InfoItem`).
- **Bonus:** Implementasi animasi menggunakan state dan `AnimatedVisibility`.

## 🚀 Cara Menjalankan Aplikasi (Android Studio)

Proyek ini menggunakan struktur standar *Kotlin Multiplatform Wizard*. Pastikan Anda telah menginstal Android Studio versi terbaru dan JDK 17+.

### Menjalankan Target Desktop (Lebih Cepat)
1. Buka proyek ini di Android Studio.
2. Tunggu proses *Gradle Sync* dan *Indexing* selesai secara penuh.
3. Pada bilah atas Android Studio (panel konfigurasi sebelah tombol Run/Play hijau), klik *dropdown* dan ubah opsi dari `composeApp` menjadi **`desktopApp`**.
4. Klik tombol **Run** (segitiga hijau) atau tekan `Shift + F10`.
5. Jendela aplikasi desktop akan otomatis muncul.

### Menjalankan Target Android
1. Buka proyek ini di Android Studio.
2. Siapkan dan jalankan *Android Emulator* (AVD) melalui Device Manager.
3. Pada bilah konfigurasi di atas, pastikan target yang terpilih adalah **`composeApp`**.
4. Klik tombol **Run**.
5. Aplikasi akan di-*build* dan diinstal ke dalam emulator Android.

## 📸 Tangkapan Layar (Screenshots)

*Berikut adalah tampilan aplikasi saat dijalankan:*

![Screenshot Aplikasi Desktop/Android](TampilanHasil.png)
