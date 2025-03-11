package com.ruma.notes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruma.notes.domain.model.Folder
import com.ruma.notes.domain.model.Note
import com.ruma.notes.domain.repositories.FolderRepository
import com.ruma.notes.domain.repositories.NoteRepository
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
    private var _folders = MutableStateFlow<List<Folder>>(emptyList())
    val folders: StateFlow<List<Folder>> = _folders

    private var _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    init {
        loadNotesAndFolders()
    }

    fun loadNotesAndFolders() {
        viewModelScope.launch {
            _folders.value = folderRepository.getRootFolders()
            _notes.value = noteRepository.getRootNotes()
        }
    }

    fun insertFolder(folderName: String) {
        viewModelScope.launch {
            folderRepository.insertFolder(
                Folder(name = folderName, parentFolderId = null)
            )
            loadNotesAndFolders()
        }
    }
}