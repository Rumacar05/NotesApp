package com.ruma.notes.data.database

import com.ruma.notes.data.database.dao.FolderDao
import com.ruma.notes.data.database.dao.NoteDao
import com.ruma.notes.data.database.entity.FolderEntity
import com.ruma.notes.data.database.entity.NoteEntity
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val folderDao: FolderDao,
    private val noteDao: NoteDao
) {
    suspend fun getRootFolders(): List<FolderEntity> = folderDao.getRootFolders()
    suspend fun getRootNotes(): List<NoteEntity> = noteDao.getRootNotes()
    suspend fun getFolderById(folderId: Long): FolderEntity? = folderDao.getFolderById(folderId)
    suspend fun getFoldersByParentId(parentFolderId: Long): List<FolderEntity> =
        folderDao.getFolderByParentId(parentFolderId)

    suspend fun getNotesByFolderId(folderId: Long): List<NoteEntity> =
        noteDao.getNotesByFolderId(folderId)

    suspend fun insertFolder(folder: FolderEntity) = folderDao.insert(folder)
    suspend fun updateFolder(folder: FolderEntity) = folderDao.update(folder)
    suspend fun deleteFolder(folder: FolderEntity) = folderDao.delete(folder)
    suspend fun getNoteById(noteId: Long) = noteDao.getNoteById(noteId)
    suspend fun insertNote(note: NoteEntity) = noteDao.insert(note)
    suspend fun deleteNote(note: NoteEntity) = noteDao.delete(note)
}