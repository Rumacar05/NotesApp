package com.ruma.notes.domain.model

import com.ruma.notes.data.database.entities.FolderEntity

data class Folder(val id: Long = 0, val name: String, val parentFolderId: Long?)

fun FolderEntity.toDomain() = Folder(id = id, name = name, parentFolderId = parentFolderId)