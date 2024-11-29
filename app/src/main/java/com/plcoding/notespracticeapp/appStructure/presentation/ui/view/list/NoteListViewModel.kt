package com.plcoding.notespracticeapp.appStructure.presentation.ui.view.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.notespracticeapp.appStructure.domain.model.Note
import com.plcoding.notespracticeapp.appStructure.domain.use_case.NoteUseCases
import com.plcoding.notespracticeapp.appStructure.domain.util.NoteOrder
import com.plcoding.notespracticeapp.appStructure.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases // wrapper class for the Notes Use Cases
) : ViewModel() {
    // shows current state of UI to user
    // i.e. what should be visible to the user

    // function triggered from UI
    private val _state = mutableStateOf(NoteListState()) // init. with empty notestate by default
    val state: State<NoteListState> = _state // exposes NoteListState to public

    private var recentlyDeletedNote: Note? = null //holds reference for recently deleted note in case of retrieval
    private var getNotesJob: Job? = null

    // calls list of stored notes by Descending date order by default
    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.Order -> {
                //check if order has changed
                //if note order is and type of ordering is the same
                //then do nothing
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }

            is NoteListEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note) // deletes note
                    recentlyDeletedNote = event.note // stores deleted note for 'undo' later
                }
            }

            is NoteListEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(
                        recentlyDeletedNote ?: return@launch
                    ) // only allows note to be inserted once, if there is no recentlyDeletedNote, this is skipped
                    recentlyDeletedNote = null // invalidates note after insertion
                }

            }

            is NoteListEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible // called from NoteListState
                )

            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel() //cancels any existing note retrieval jobs to stop overlapping operations, allows app to run more smoothly
        getNotesJob = noteUseCases.getAllNotesUseCase(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}