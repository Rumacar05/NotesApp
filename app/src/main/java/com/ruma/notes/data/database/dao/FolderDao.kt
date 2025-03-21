package com.ruma.notes.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ruma.notes.data.database.entities.FolderEntity

@Dao
interface FolderDao {
    @Insert
    suspend fun insert(folder: FolderEntity)

    @Update
    suspend fun update(folder: FolderEntity)

    @Delete
    suspend fun delete(folder: FolderEntity)

    @Query("SELECT * FROM folder_table WHERE parentFolderId IS NULL")
    suspend fun getRootFolders(): List<FolderEntity>

    @Query("SELECT * FROM folder_table WHERE id = :folderId")
    suspend fun getFolderById(folderId: Long): FolderEntity?

    @Query("SELECT * FROM folder_table WHERE parentFolderId = :parentFolderId")
    suspend fun getFolderByParentId(parentFolderId: Long): List<FolderEntity>
}