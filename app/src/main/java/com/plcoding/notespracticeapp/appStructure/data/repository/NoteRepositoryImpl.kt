package com.plcoding.notespracticeapp.appStructure.data.repository

import com.plcoding.notespracticeapp.appStructure.data.data_source.NoteDao
import com.plcoding.notespracticeapp.appStructure.domain.model.Note
import com.plcoding.notespracticeapp.appStructure.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow


class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    // ctrl + i to generate fun based on NoteDao
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}