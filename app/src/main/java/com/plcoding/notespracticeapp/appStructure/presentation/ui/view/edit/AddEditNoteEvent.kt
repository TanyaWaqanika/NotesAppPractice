package com.plcoding.notespracticeapp.appStructure.presentation.ui.view.edit

import androidx.compose.ui.focus.FocusState

// for every ui interaction, there will be an event
sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class EnteredContent(val value: String) : AddEditNoteEvent()
    data class ChangedContentFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class ChangeColour(val colour: Int) : AddEditNoteEvent()
    data object SaveNote : AddEditNoteEvent()
}