package com.plcoding.notespracticeapp.appStructure.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plcoding.notespracticeapp.appStructure.domain.model.Note
import kotlinx.coroutines.flow.Flow

//Dao interfaces are used for the CRUD operations of an entity
@Dao
interface NoteDao {

    // GET/READ
    @Query("SELECT * FROM note") // room generates implementation for SQLquery
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    //CREATE OR UPDATE
    // If note with same id as existing id in database is added
    // this will update existing entry with incoming info
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}