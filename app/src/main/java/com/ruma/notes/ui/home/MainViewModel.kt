package com.ruma.notes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruma.notes.domain.model.Folder
import com.ruma.notes.domain.model.Note
import com.ruma.notes.domain.repositories.FolderRepository
import com.ruma.notes.domain.repositories.NoteRepository
import com.ruma.notes.domain.usecase.InsertFolderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val folderRepository: FolderRepository,
    private val noteRepository: NoteRepository,
    private val insertFolderUseCase: InsertFolderUseCase
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
            _folders.value = async { folderRepository.getRootFolders() }.await()
            _notes.value = async { noteRepository.getRootNotes() }.await()
        }
    }

    fun insertFolder(folderName: String) {
        viewModelScope.launch {
            insertFolderUseCase(folderName, null)
            loadNotesAndFolders()
        }
    }
}