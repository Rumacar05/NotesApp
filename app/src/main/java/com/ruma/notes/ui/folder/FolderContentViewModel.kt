package com.ruma.notes.ui.folder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruma.notes.data.database.NotesRepository
import com.ruma.notes.data.database.entity.FolderEntity
import com.ruma.notes.data.database.entity.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderContentViewModel @Inject constructor(private val repository: NotesRepository) :
    ViewModel() {
    private var _currentFolder = MutableStateFlow<FolderEntity?>(null)
    val currentFolder = _currentFolder

    private var _folders = MutableStateFlow<List<FolderEntity>>(emptyList())
    val folder = _folders

    private var _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes = _notes

    fun loadFolder(folderId: Long) {
        viewModelScope.launch {
            _currentFolder.value = repository.getFolderById(folderId)
            _folders.value = repository.getFoldersByParentId(folderId)
            _notes.value = repository.getNotesByFolderId(folderId)
        }
    }
}