package org.example.project.data

import app.cash.sqldelight.db.SqlDriver

// 'expect' berarti kita meminta sistem untuk menyediakan implementasi
// asli dari driver ini di masing-masing platform (Android/iOS)
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}