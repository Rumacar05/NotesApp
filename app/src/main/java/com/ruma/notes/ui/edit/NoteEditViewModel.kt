package com.ruma.notes.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruma.notes.data.database.NotesRepository
import com.ruma.notes.data.database.entity.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(private val repository: NotesRepository) : ViewModel() {
    private val _note = MutableStateFlow<NoteEntity?>(null)
    val note: StateFlow<NoteEntity?> = _note

    fun loadNoteByID(noteId: Long) {
        viewModelScope.launch {
            _note.value = repository.getNoteById(noteId)
        }
    }

    fun saveNote(title: String, content: String, folderId: Long? = null) {
        viewModelScope.launch {
            val currentNote = _note.value
            val note = currentNote?.copy(title = title, content = content)
                ?: NoteEntity(
                    title = title,
                    content = content,
                    folderId = folderId,
                    timestamp = System.currentTimeMillis()
                )

            repository.insertNote(note)
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            val currentNote = _note.value
            if (currentNote != null) {
                repository.deleteNote(currentNote)
            }
        }
    }
}