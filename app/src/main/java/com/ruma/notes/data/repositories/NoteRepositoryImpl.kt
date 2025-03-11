package com.ruma.notes.data.repositories

import com.ruma.notes.data.database.dao.NoteDao
import com.ruma.notes.data.database.entities.NoteEntity
import com.ruma.notes.domain.model.Note
import com.ruma.notes.domain.model.toDomain
import com.ruma.notes.domain.repositories.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun getRootNotes(): List<Note> {
        return noteDao.getRootNotes()
            .map { note -> note.toDomain() }
    }

    override suspend fun getNotesByFolderId(folderId: Long): List<Note> {
        return noteDao.getNotesByFolderId(folderId)
            .map { note -> note.toDomain() }
    }

    override suspend fun getNoteById(noteId: Long): Note? {
        return noteDao.getNoteById(noteId)?.toDomain()
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insert(
            NoteEntity(
                id = note.id,
                title = note.title,
                content = note.content,
                folderId = note.folderId,
                timestamp = note.timestamp
            )
        )
    }

    override suspend fun updateNote(note: Note) {
        noteDao.update(
            NoteEntity(
                id = note.id,
                title = note.title,
                content = note.content,
                folderId = note.folderId,
                timestamp = note.timestamp
            )
        )
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.delete(
            NoteEntity(
                id = note.id,
                title = note.title,
                content = note.content,
                folderId = note.folderId,
                timestamp = note.timestamp
            )
        )
    }
}