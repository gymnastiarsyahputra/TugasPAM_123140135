package org.example.project.navigation

sealed class Screen(val route: String) {
    // Bottom Nav Tabs
    object Notes : Screen("notes")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")

    // Halaman lain
    object AddNote : Screen("add_note")

    // Halaman dengan argumen (noteId)
    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }
    object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Int) = "edit_note/$noteId"
    }
}