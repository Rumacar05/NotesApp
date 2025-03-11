package com.ruma.notes.di

import android.content.Context
import androidx.room.Room
import com.ruma.notes.data.database.dao.FolderDao
import com.ruma.notes.data.database.dao.NoteDao
import com.ruma.notes.data.database.db.AppDatabase
import com.ruma.notes.data.repositories.FolderRepository
import com.ruma.notes.data.repositories.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val NOTES_DATABASE_NAME = "notes_db"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            NOTES_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesFolderDao(database: AppDatabase) = database.folderDao()

    @Provides
    @Singleton
    fun providesNoteDao(database: AppDatabase) = database.noteDao()

    @Provides
    @Singleton
    fun provideFolderRepository(folderDao: FolderDao) = FolderRepository(folderDao)

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao) = NoteRepository(noteDao)
}