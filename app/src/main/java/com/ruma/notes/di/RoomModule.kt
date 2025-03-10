package com.ruma.notes.di

import android.content.Context
import androidx.room.Room
import com.ruma.notes.data.database.dao.FolderDao
import com.ruma.notes.data.database.dao.NoteDao
import com.ruma.notes.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "notes_db"
        ).build()
    }

    @Provides
    fun providesFolderDao(database: AppDatabase) : FolderDao {
        return database.folderDao()
    }

    @Provides
    fun providesNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }
}