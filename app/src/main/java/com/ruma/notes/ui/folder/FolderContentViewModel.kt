package com.ruma.notes.ui.folder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruma.notes.data.repositories.NoteRepository
import com.ruma.notes.data.database.entities.FolderEntity
import com.ruma.notes.data.database.entities.NoteEntity
import com.ruma.notes.data.repositories.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderContentViewModel @Inject constructor(
    private val folderRepository: FolderRepository,
    private val noteRepository: NoteRepository
) :
    ViewModel() {
    private var _currentFolder = MutableStateFlow<FolderEntity?>(null)
    val currentFolder = _currentFolder

    private var _folders = MutableStateFlow<List<FolderEntity>>(emptyList())
    val folder = _folders

    private var _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes = _notes

    fun loadFolder(folderId: Long) {
        viewModelScope.launch {
            _currentFolder.value = folderRepository.getFolderById(folderId)
            _folders.value = folderRepository.getFoldersByParentId(folderId)
            _notes.value = noteRepository.getNotesByFolderId(folderId)
        }
    }

    fun insertFolder(folder: FolderEntity) {
        viewModelScope.launch {
            folderRepository.insertFolder(folder)
            folder.parentFolderId?.let { loadFolder(it) }
        }
    }

    fun updateFolder(folderName: String) {
        viewModelScope.launch {
            val folder = _currentFolder.value
            if (folder != null && folder.name != folderName) {
                val updateFolder = folder.copy(name = folderName)
                folderRepository.updateFolder(updateFolder)
            }
        }
    }

    fun deleteFolder() {
        viewModelScope.launch {
            val folder = _currentFolder.value
            if (folder != null) {
                folderRepository.deleteFolder(folder)
            }
        }
    }
}