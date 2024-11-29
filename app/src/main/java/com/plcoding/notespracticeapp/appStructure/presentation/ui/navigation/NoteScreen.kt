package com.plcoding.notespracticeapp.appStructure.presentation.ui.navigation

sealed class NoteScreen(val route: String) {
    data object NotesScreen : NoteScreen("notes_screen")
    data object AddEditNoteScreen : NoteScreen("add_edit_note_screen")
}