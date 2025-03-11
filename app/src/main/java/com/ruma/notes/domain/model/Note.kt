package com.ruma.notes.domain.model

import com.ruma.notes.data.database.entities.NoteEntity

data class Note(
    val id: Long = 0,
    val title: String,
    val content: String,
    val folderId: Long?,
    val timestamp: Long
)

fun NoteEntity.toDomain() =
    Note(id = id, title = title, content = content, folderId = folderId, timestamp = timestamp)
