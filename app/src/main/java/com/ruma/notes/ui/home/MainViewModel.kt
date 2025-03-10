package com.ruma.notes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruma.notes.data.database.NotesRepository
import com.ruma.notes.data.database.entity.FolderEntity
import com.ruma.notes.data.database.entity.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NotesRepository): ViewModel() {
    private val _folders = MutableStateFlow<List<FolderEntity>>(emptyList())
    val folders: StateFlow<List<FolderEntity>> = _folders

    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes: StateFlow<List<NoteEntity>> = _notes

    init {
        loadNotesAndFolders()
    }

    fun loadNotesAndFolders() {
        viewModelScope.launch {
            _folders.value = repository.getRootFolders()
            _notes.value = repository.getRootNotes()
        }
    }
}