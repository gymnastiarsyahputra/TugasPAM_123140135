# Tugas Praktikum 6 PAM - News Reader App

Repositori ini berisi implementasi Tugas Praktikum Pertemuan 6 mata kuliah Pengembangan Aplikasi Mobile (PAM), Program Studi Teknik Informatika, Institut Teknologi Sumatera (ITERA). [cite_start]Proyek ini berfokus pada integrasi **Networking** dan **REST API** menggunakan Ktor Client di Compose Multiplatform[cite: 1252, 1253].

## 🌟 Fitur Utama
Aplikasi ini telah memenuhi seluruh kriteria tugas, antara lain:
1. [cite_start]**API Integration**: Mengambil data berita secara *real-time* menggunakan Ktor Client[cite: 1754, 1767].
2. [cite_start]**List & Detail View**: Menampilkan daftar artikel dengan judul, deskripsi, dan gambar, serta navigasi ke halaman detail saat artikel diklik[cite: 1755, 1756].
3. [cite_start]**Pull to Refresh**: Fitur untuk memperbarui daftar berita secara manual[cite: 1757].
4. [cite_start]**UI States Management**: Implementasi status *Loading*, *Success*, dan *Error* (termasuk penanganan jika koneksi internet terputus)[cite: 1758].
5. [cite_start]**Repository Pattern**: Abstraksi data layer untuk memisahkan logika API dari ViewModel[cite: 1759, 1767].

## 🛠️ Teknologi & API
- [cite_start]**API**: [JSONPlaceholder](https://jsonplaceholder.typicode.com/) (menggunakan endpoint `/posts`)[cite: 1754, 1775].
- [cite_start]**HTTP Client**: [Ktor Client](https://ktor.io/)[cite: 1253, 1355].
- [cite_start]**Serialization**: [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) untuk parsing JSON[cite: 1478, 1481].
- **Image Loader**: Coil3 untuk pemuatan gambar secara asinkron.
- [cite_start]**Architecture**: MVVM (Model-View-ViewModel) dengan Repository Pattern[cite: 1531, 1534].

## 📸 Tangkapan Layar (Screenshots)
[cite_start]*Berikut adalah tampilan aplikasi dalam berbagai status UI:*

