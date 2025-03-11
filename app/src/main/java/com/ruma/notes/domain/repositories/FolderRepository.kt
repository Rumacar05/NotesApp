package com.ruma.notes.domain.repositories

import com.ruma.notes.domain.model.Folder

interface FolderRepository {
    suspend fun getRootFolders(): List<Folder>
    suspend fun getFolderById(folderId: Long): Folder?
    suspend fun getFoldersByParentId(parentFolderId: Long): List<Folder>
    suspend fun insertFolder(folder: Folder)
    suspend fun updateFolder(folder: Folder)
    suspend fun deleteFolder(folder: Folder)
}