package com.plcoding.notespracticeapp.appStructure.domain.use_case

import com.plcoding.notespracticeapp.appStructure.domain.model.Note
import com.plcoding.notespracticeapp.appStructure.domain.repository.NoteRepository
import javax.inject.Inject

//add injection constructor
class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}