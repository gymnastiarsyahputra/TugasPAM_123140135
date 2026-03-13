package org.example.project

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

fun main() = runBlocking {
    val simulator = NewsFeedSimulator()

    launch {
        simulator.readNewsCount.collect { count ->
            println("--- [Info] Total Berita Dibaca: $count ---")
        }
    }

    println("Memulai News Feed Simulator...")

    simulator.getNewsStream()
        .filter { news ->
            news.category == "Teknologi" || news.category == "Kampus"
        }
        .map { news ->
            if (news.id == 5) throw IllegalStateException("Koneksi ke server berita terputus!")
            "[${news.category.uppercase()}] ${news.title}"
        }
        .catch { e ->
            println("\n❌ [ERROR STREAM]: Gagal memproses aliran berita -> ${e.message}")
        }
        .collect { formattedNews ->
            println("\nBerita Masuk: $formattedNews")

            try {
                val detailDeferred = async(Dispatchers.IO) {
                    simulator.fetchNewsDetail(Random.nextInt(1, 100))
                }

                val detail = detailDeferred.await()
                println("Detail: $detail")

                simulator.readNews()

            } catch (e: Exception) {
                println("❌ [ERROR DETAIL]: Gagal mengunduh isi detail berita -> ${e.message}")
            }
        }
}