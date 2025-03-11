package com.ruma.notes.domain.usecase

import com.ruma.notes.domain.model.Note
import com.ruma.notes.domain.repositories.NoteRepository
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(
        currentNote: Note?,
        title: String,
        content: String,
        folderId: Long?
    ): Note? {
        var savedNote: Note? = null

        if (currentNote != null) {
            savedNote = currentNote.copy(title = title, content = content)
            noteRepository.updateNote(savedNote)
        } else {
            if (title.isNotEmpty() || content.isNotEmpty()) {
                savedNote = Note(
                    title = title,
                    content = content,
                    folderId = folderId,
                    timestamp = System.currentTimeMillis()
                )
                noteRepository.insertNote(savedNote)
            }
        }

        return savedNote
    }
}