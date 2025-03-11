package com.ruma.notes.domain.repositories

import com.ruma.notes.domain.model.Note

interface NoteRepository {
    suspend fun getRootNotes(): List<Note>
    suspend fun getNotesByFolderId(folderId: Long): List<Note>
    suspend fun getNoteById(noteId: Long): Note?
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
}