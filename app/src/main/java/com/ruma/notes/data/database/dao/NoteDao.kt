package com.ruma.notes.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ruma.notes.data.database.entities.NoteEntity

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Query("SELECT * FROM note_table WHERE id = :noteId")
    suspend fun getNoteById(noteId: Long): NoteEntity?

    @Query("SELECT * FROM note_table WHERE folderId IS NULL")
    suspend fun getRootNotes() : List<NoteEntity>

    @Query("SELECT * FROM note_table WHERE folderId = :folderId")
    suspend fun getNotesByFolderId(folderId: Long): List<NoteEntity>
}