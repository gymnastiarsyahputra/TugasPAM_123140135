import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// --- 1. DATA MODEL ---
data class NewsItem(
    val id: Int,
    val title: String,
    val category: String,
    val summary: String
)

// --- 2. SIMULATOR (Repository) ---
class NewsRepository {
    // Kumpulan data simulasi
    private val rawNews = listOf(
        NewsItem(1, "Kotlin 2.0 Rilis", "Teknologi", "Fitur baru compiler K2."),
        NewsItem(2, "Harga Saham Naik", "Ekonomi", "IHSG menghijau hari ini."),
        NewsItem(3, "Update Android Studio", "Teknologi", "Perbaikan bug major."),
        NewsItem(4, "Timnas Menang", "Olahraga", "Skor akhir 2-0."),
        NewsItem(5, "AI Menggantikan Coding?", "Teknologi", "Diskusi panas di forum.")
    )

    // Fitur 1: Flow yang mensimulasikan data berita baru setiap 2 detik
    fun getNewsStream(): Flow<NewsItem> = flow {
        for (news in rawNews) {
            delay(2000) // Simulasi delay 2 detik per berita
            emit(news)
        }
    }

    // Fitur 5: Coroutines untuk mengambil detail berita secara async
    suspend fun fetchNewsDetail(id: Int): String {
        delay(1000) // Simulasi network call yang berat
        return "Detail lengkap konten berita ID-$id (Loaded asynchronously)"
    }
}

// --- 3. LOGIC MANAGEMENT (ViewModel Concept) ---
class NewsViewModel(private val repository: NewsRepository) {

    // Fitur 4: StateFlow untuk menyimpan jumlah berita yang sudah dibaca
    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    fun startNewsFeed(targetCategory: String) = runBlocking {
        println("=== Memulai Simulator News Feed ===")
        println("Filter Kategori: $targetCategory\n")

        // Menjalankan collector untuk StateFlow (Counter) di background
        val counterJob = launch {
            readCount.collect { count ->
                println("[Status] Total Berita Dibaca: $count")
            }
        }

        repository.getNewsStream()
            // Fitur 2: Filter berita berdasarkan kategori tertentu
            .filter { it.category == targetCategory }
            // Fitur 3: Transform data menjadi format yang ditampilkan
            .map { news ->
                "BREAKING NEWS [${news.category.uppercase()}]: ${news.title}" to news
            }
            // Error Handling (Bonus poin rubrik )
            .catch { e ->
                println("Error saat mengambil berita: ${e.message}")
            }
            .collect { (formattedTitle, newsItem) ->
                // Tampilkan format hasil transformasi
                println(formattedTitle)

                // Simulasi mengambil detail secara async (Fitur 5)
                val detail = async { repository.fetchNewsDetail(newsItem.id) }
                println("   -> ${detail.await()}")

                // Update StateFlow (Fitur 4)
                _readCount.value += 1
                println("------------------------------------------------")
            }

        // Cleanup job agar program berhenti
        counterJob.cancel()
        println("\n=== Simulasi Selesai ===")
    }
}

// --- 4. MAIN PROGRAM ---
fun main() {
    val repository = NewsRepository()
    val viewModel = NewsViewModel(repository)

    // Jalankan simulasi dengan filter kategori "Teknologi"
    viewModel.startNewsFeed("Teknologi")
}