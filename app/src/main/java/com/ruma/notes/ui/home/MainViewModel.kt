package com.ruma.notes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruma.notes.data.repositories.NoteRepository
import com.ruma.notes.data.database.entities.FolderEntity
import com.ruma.notes.data.database.entities.NoteEntity
import com.ruma.notes.data.repositories.FolderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val folderRepository: FolderRepository,
    private val noteRepository: NoteRepository
) : ViewModel() {
    private var _folders = MutableStateFlow<List<FolderEntity>>(emptyList())
    val folders: StateFlow<List<FolderEntity>> = _folders

    private var _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes: StateFlow<List<NoteEntity>> = _notes

    init {
        loadNotesAndFolders()
    }

    fun loadNotesAndFolders() {
        viewModelScope.launch {
            _folders.value = folderRepository.getRootFolders()
            _notes.value = noteRepository.getRootNotes()
        }
    }

    fun insertFolder(folder: FolderEntity) {
        viewModelScope.launch {
            folderRepository.insertFolder(folder)
            loadNotesAndFolders()
        }
    }
}