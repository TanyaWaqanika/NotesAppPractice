package com.plcoding.notespracticeapp.appStructure.domain.use_case

import com.plcoding.notespracticeapp.appStructure.domain.model.InvalidNoteException
import com.plcoding.notespracticeapp.appStructure.domain.model.Note
import com.plcoding.notespracticeapp.appStructure.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
){
    @Throws(InvalidNoteException::class)
    // gets note to be inserted
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("Please...please give me a title...")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty!")
        }
        repository.insertNote(note)
    }
}