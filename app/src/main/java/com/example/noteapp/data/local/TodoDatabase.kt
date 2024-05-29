package com.example.noteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noteapp.domain.model.Todo

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [Todo::class],
    version = 2,
    exportSchema = true
)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}