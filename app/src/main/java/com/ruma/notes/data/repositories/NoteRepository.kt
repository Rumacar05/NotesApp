package com.ruma.notes.data.repositories

import com.ruma.notes.data.database.dao.NoteDao
import com.ruma.notes.data.database.entities.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {
    suspend fun getRootNotes(): List<NoteEntity> = noteDao.getRootNotes()

    suspend fun getNotesByFolderId(folderId: Long): List<NoteEntity> =
        noteDao.getNotesByFolderId(folderId)

    suspend fun getNoteById(noteId: Long) = noteDao.getNoteById(noteId)
    suspend fun insertNote(note: NoteEntity) = noteDao.insert(note)
    suspend fun deleteNote(note: NoteEntity) = noteDao.delete(note)
}