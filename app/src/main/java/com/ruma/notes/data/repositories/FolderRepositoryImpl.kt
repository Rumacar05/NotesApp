package com.ruma.notes.data.repositories

import com.ruma.notes.data.database.dao.FolderDao
import com.ruma.notes.data.database.entities.FolderEntity
import com.ruma.notes.domain.model.Folder
import com.ruma.notes.domain.model.toDomain
import com.ruma.notes.domain.repositories.FolderRepository
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(private val folderDao: FolderDao) :
    FolderRepository {
    override suspend fun getRootFolders(): List<Folder> {
        return folderDao.getRootFolders()
            .map { folder -> folder.toDomain() }
    }

    override suspend fun getFolderById(folderId: Long): Folder? {
        return folderDao.getFolderById(folderId)?.toDomain()
    }

    override suspend fun getFoldersByParentId(parentFolderId: Long): List<Folder> {
        return folderDao.getFolderByParentId(parentFolderId)
            .map { folder -> folder.toDomain() }
    }

    override suspend fun insertFolder(folder: Folder) {
        folderDao.insert(FolderEntity(
            id = folder.id,
            name = folder.name,
            parentFolderId = folder.parentFolderId
        ))
    }
    override suspend fun updateFolder(folder: Folder) {
        folderDao.update(FolderEntity(
            id = folder.id,
            name = folder.name,
            parentFolderId = folder.parentFolderId
        ))
    }
    override suspend fun deleteFolder(folder: Folder) {
        folderDao.delete(FolderEntity(
            id = folder.id,
            name = folder.name,
            parentFolderId = folder.parentFolderId
        ))
    }
}