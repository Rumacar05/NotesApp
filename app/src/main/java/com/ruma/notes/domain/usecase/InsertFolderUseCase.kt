package com.ruma.notes.domain.usecase

import com.ruma.notes.domain.model.Folder
import com.ruma.notes.domain.repositories.FolderRepository
import javax.inject.Inject

class InsertFolderUseCase @Inject constructor(private val folderRepository: FolderRepository) {
    suspend operator fun invoke(folderName: String, parentId: Long?) {
        return folderRepository.insertFolder(Folder(name = folderName, parentFolderId = parentId))
    }
}