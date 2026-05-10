package org.example.project.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.notes.db.Note
import com.example.notes.db.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

class NoteRepository(database: NotesDatabase) {
    private val queries = database.noteQueries //[cite: 5]

    fun getAllNotes(): Flow<List<Note>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO) //[cite: 5]
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        return queries.search(query, query)
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    suspend fun insertNote(title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds() //[cite: 5]
        queries.insert(title, content, now, now) //[cite: 5]
    }

    suspend fun updateNote(id: Long, title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        queries.update(title, content, now, id) //[cite: 5]
    }

    suspend fun deleteNote(id: Long) {
        queries.delete(id) //[cite: 5]
    }
}