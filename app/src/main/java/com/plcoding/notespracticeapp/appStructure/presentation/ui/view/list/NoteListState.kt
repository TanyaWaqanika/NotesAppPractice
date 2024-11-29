package com.plcoding.notespracticeapp.appStructure.presentation.ui.view.list

import com.plcoding.notespracticeapp.appStructure.domain.model.Note
import com.plcoding.notespracticeapp.appStructure.domain.util.NoteOrder
import com.plcoding.notespracticeapp.appStructure.domain.util.OrderType

// Default state of the app upon opening
data class NoteListState(
    val notes: List<Note> = emptyList(), //default state
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending), // default order of list
    val isOrderSectionVisible: Boolean = false // default toggle state of OrderSection
)
