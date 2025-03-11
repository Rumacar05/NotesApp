package com.ruma.notes.data.repositories

import com.ruma.notes.data.database.dao.FolderDao
import com.ruma.notes.data.database.entities.FolderEntity
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(private val folderDao: FolderDao) {
    suspend fun getRootFolders(): List<FolderEntity> = folderDao.getRootFolders()
    suspend fun getFolderById(folderId: Long): FolderEntity? = folderDao.getFolderById(folderId)
    suspend fun getFoldersByParentId(parentFolderId: Long): List<FolderEntity> =
        folderDao.getFolderByParentId(parentFolderId)

    suspend fun insertFolder(folder: FolderEntity) = folderDao.insert(folder)
    suspend fun updateFolder(folder: FolderEntity) = folderDao.update(folder)
    suspend fun deleteFolder(folder: FolderEntity) = folderDao.delete(folder)
}