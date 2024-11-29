package com.plcoding.notespracticeapp.appStructure.presentation.ui.view.list

import com.plcoding.notespracticeapp.appStructure.domain.model.Note
import com.plcoding.notespracticeapp.appStructure.domain.util.NoteOrder

// collection of all possible events that can be done to notes on app
sealed class NoteListEvent {
    data class Order(val noteOrder: NoteOrder) : NoteListEvent()
    data class DeleteNote(val note: Note) : NoteListEvent()
    data object RestoreNote : NoteListEvent()
    data object ToggleOrderSection : NoteListEvent()
}