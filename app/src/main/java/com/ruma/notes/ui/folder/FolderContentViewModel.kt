package com.ruma.notes.ui.folder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruma.notes.domain.model.Folder
import com.ruma.notes.domain.model.Note
import com.ruma.notes.domain.repositories.FolderRepository
import com.ruma.notes.domain.repositories.NoteRepository
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
    private var _currentFolder = MutableStateFlow<Folder?>(null)
    val currentFolder = _currentFolder

    private var _folders = MutableStateFlow<List<Folder>>(emptyList())
    val folder = _folders

    private var _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes

    fun loadFolder(folderId: Long) {
        viewModelScope.launch {
            _currentFolder.value = folderRepository.getFolderById(folderId)
            _folders.value = folderRepository.getFoldersByParentId(folderId)
            _notes.value = noteRepository.getNotesByFolderId(folderId)
        }
    }

    fun insertFolder(folderName: String, parentFolderId: Long?) {
        viewModelScope.launch {
            val folder = Folder(name = folderName, parentFolderId = parentFolderId)
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