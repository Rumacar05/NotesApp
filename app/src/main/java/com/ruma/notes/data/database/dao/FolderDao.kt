package com.ruma.notes.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruma.notes.data.database.entity.FolderEntity

@Dao
interface FolderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(folder: FolderEntity)

    @Delete
    suspend fun delete(folder: FolderEntity)

    @Query("SELECT * FROM folders WHERE parentFolderId IS NULL")
    suspend fun getRootFolders() : List<FolderEntity>

    @Query("SELECT * FROM folders WHERE id = :folderId")
    suspend fun getFolderById(folderId: Long): FolderEntity?

    @Query("SELECT * FROM folders WHERE parentFolderId = :parentFolderId")
    suspend fun getFolderByParentId(parentFolderId: Long): List<FolderEntity>
}