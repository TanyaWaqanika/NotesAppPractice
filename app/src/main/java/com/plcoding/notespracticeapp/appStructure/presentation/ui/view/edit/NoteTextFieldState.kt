package com.plcoding.notespracticeapp.appStructure.presentation.ui.view.edit

// class to hold empty fields for hint text when clicked
data class NoteTextFieldState(
    val stateText: String = "",
    val stateHint: String = "",
    val stateIsHintVisible: Boolean = true
)