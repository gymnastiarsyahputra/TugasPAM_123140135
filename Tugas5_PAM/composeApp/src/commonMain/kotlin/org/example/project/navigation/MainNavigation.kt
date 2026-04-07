package org.example.project.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import org.example.project.screens.*
import org.example.project.viewmodel.NotesViewModel
import androidx.compose.ui.unit.dp

@Composable
fun MainNavigation(viewModel: NotesViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Setup Navigation Drawer (Bonus)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu Utama", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.headlineSmall)
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Catatan Ku") },
                    selected = false,
                    icon = { Icon(Icons.Default.List, null) },
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.Notes.route)
                    }
                )
            }
        }
    ) {
        // Setup Scaffold untuk Bottom Navigation
        Scaffold(
            bottomBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Tampilkan BottomBar HANYA di halaman utama
                if (currentRoute == Screen.Notes.route || currentRoute == Screen.Favorites.route || currentRoute == Screen.Profile.route) {
                    NavigationBar {
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Home, contentDescription = "Notes") },
                            label = { Text("Notes") },
                            selected = currentRoute == Screen.Notes.route,
                            onClick = {
                                navController.navigate(Screen.Notes.route) {
                                    popUpTo(Screen.Notes.route)
                                    launchSingleTop = true
                                }
                            }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                            label = { Text("Favorites") },
                            selected = currentRoute == Screen.Favorites.route,
                            onClick = {
                                navController.navigate(Screen.Favorites.route) {
                                    popUpTo(Screen.Notes.route)
                                    launchSingleTop = true
                                }
                            }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                            label = { Text("Profile") },
                            selected = currentRoute == Screen.Profile.route,
                            onClick = {
                                navController.navigate(Screen.Profile.route) {
                                    popUpTo(Screen.Notes.route)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            // --- NAV HOST ---
            NavHost(
                navController = navController,
                startDestination = Screen.Notes.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                // Tab 1: Notes
                composable(Screen.Notes.route) {
                    NotesScreen(
                        viewModel = viewModel,
                        onNavigateToDetail = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) },
                        onNavigateToAdd = { navController.navigate(Screen.AddNote.route) }
                    )
                }
                // Tab 2: Favorites
                composable(Screen.Favorites.route) { FavoritesScreen() }

                // Tab 3: Profile
                composable(Screen.Profile.route) { ProfileScreen() }

                // Detail Note (Passing Argument noteId)
                composable(
                    route = Screen.NoteDetail.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                    NoteDetailScreen(
                        noteId = noteId,
                        viewModel = viewModel,
                        onNavigateToEdit = { id -> navController.navigate(Screen.EditNote.createRoute(id)) },
                        onBack = { navController.popBackStack() }
                    )
                }

                // Add Note
                composable(Screen.AddNote.route) {
                    AddNoteScreen(onBack = { navController.popBackStack() })
                }

                // Edit Note (Passing Argument noteId)
                composable(
                    route = Screen.EditNote.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                    EditNoteScreen(
                        noteId = noteId,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}