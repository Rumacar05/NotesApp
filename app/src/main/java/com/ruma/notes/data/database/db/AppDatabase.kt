package com.ruma.notes.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ruma.notes.data.database.dao.FolderDao
import com.ruma.notes.data.database.dao.NoteDao
import com.ruma.notes.data.database.entities.FolderEntity
import com.ruma.notes.data.database.entities.NoteEntity

@Database(entities = [FolderEntity::class, NoteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao
}