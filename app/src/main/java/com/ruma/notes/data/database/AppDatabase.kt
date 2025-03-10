package com.ruma.notes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ruma.notes.data.database.dao.FolderDao
import com.ruma.notes.data.database.dao.NoteDao

@Database(entities = [FolderDao::class, NoteDao::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao
}