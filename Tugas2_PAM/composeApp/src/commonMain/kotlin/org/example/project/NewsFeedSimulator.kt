package org.example.project

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

// Merepresentasikan struktur data berita
data class News(val id: Int, val title: String, val category: String)

class NewsFeedSimulator {
    private val _readNewsCount = MutableStateFlow(0)
    val readNewsCount: StateFlow<Int> = _readNewsCount.asStateFlow()

    fun getNewsStream(): Flow<News> = flow {
        var idCounter = 1
        val categories = listOf("Teknologi", "Olahraga", "Kampus")

        while (true) {
            delay(2000)
            val news = News(
                id = idCounter,
                title = "Pembaruan Sistem $idCounter",
                category = categories.random()
            )
            emit(news)
            idCounter++
        }
    }

    suspend fun fetchNewsDetail(newsId: Int): String {
        delay(1000)
        return "Ini adalah teks detail panjang untuk berita ID $newsId..."
    }

    fun readNews() {
        _readNewsCount.value++
    }
}