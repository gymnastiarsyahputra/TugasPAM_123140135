package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.project.ui.NewsDetailScreen
import org.example.project.ui.NewsListScreen
import org.example.project.viewmodel.NewsViewModel

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        // Inisialisasi ViewModel secara global
        val newsViewModel: NewsViewModel = viewModel { NewsViewModel() }

        NavHost(navController = navController, startDestination = "news_list") {
            // Route 1: List Berita
            composable("news_list") {
                NewsListScreen(
                    viewModel = newsViewModel,
                    onNavigateToDetail = { id ->
                        navController.navigate("news_detail/$id")
                    }
                )
            }

            // Route 2: Detail Berita
            composable(
                route = "news_detail/{postId}",
                arguments = listOf(navArgument("postId") { type = NavType.IntType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getInt("postId") ?: 0
                NewsDetailScreen(
                    postId = postId,
                    viewModel = newsViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}