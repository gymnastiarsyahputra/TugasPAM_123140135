package org.example.project.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// 1. Model Data
@Serializable
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

// 2. Repository Pattern
class NewsRepository {
    // Setup Ktor Client
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private val baseUrl = "https://jsonplaceholder.typicode.com"

    // Fetch semua berita
    suspend fun getNews(): Result<List<Post>> {
        return try {
            val response: List<Post> = client.get("$baseUrl/posts").body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Fetch detail berita by ID
    suspend fun getNewsById(id: Int): Result<Post> {
        return try {
            val response: Post = client.get("$baseUrl/posts/$id").body()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}