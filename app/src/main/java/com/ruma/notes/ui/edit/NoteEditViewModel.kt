package com.ruma.notes.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruma.notes.data.database.entities.NoteEntity
import com.ruma.notes.data.repositories.NoteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(private val repository: NoteRepositoryImpl) :
    ViewModel() {
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

            if (currentNote != null) {
                repository.updateNote(currentNote.copy(title = title, content = content))
            } else {
                if (title.isNotEmpty() || content.isNotEmpty()) {
                    val newNote = NoteEntity(
                        title = title,
                        content = content,
                        folderId = folderId,
                        timestamp = System.currentTimeMillis()
                    )
                    repository.insertNote(newNote)
                    _note.value = newNote
                }
            }
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