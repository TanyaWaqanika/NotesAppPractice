package com.plcoding.notespracticeapp.appStructure.domain.use_case

// class to be injected into viewModel, continues all relevant use cases
data class NoteUseCases(
    val getAllNotesUseCase: GetAllNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase
)
