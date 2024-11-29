package com.plcoding.notespracticeapp.appStructure.presentation.ui.view.edit


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.notespracticeapp.appStructure.domain.model.InvalidNoteException
import com.plcoding.notespracticeapp.appStructure.domain.model.Note
import com.plcoding.notespracticeapp.appStructure.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//viewModel holds the content that will be displayed by the screen
@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // exposing all states required to add/edit/update note
    // mutableStateOf is used to indicate values that can change
    // typical to have mutableState as a private class
    // then expose it publicly as State<T>
    private val _noteTitle =
        mutableStateOf(NoteTextFieldState(stateHint = "Note title"))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent =
        mutableStateOf(NoteTextFieldState(stateHint = "Write me like one of your french girls"))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColour =
        mutableIntStateOf(Note.noteColours.random().toArgb()) //default random colour
    val noteColour: State<Int> = _noteColour

    // MutableSharedFlow used for broadcasting events
    private val _eventFlow =
        MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        // saves and restores data across configuration changes
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) //checks if noteId is valid to launch viewModel
                {
                viewModelScope.launch {
                    noteUseCases.getNoteUseCase(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            stateText = note.title,
                            stateIsHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            stateText = note.content,
                            stateIsHintVisible = false
                        )
                        _noteColour.intValue = note.colour
                    }
                }
            }
        }
    }

    // Events pulled in from AddEditNoteEvent, controls what happens when user interacts with app
    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            //if only title is entered/edited, then ONLY update
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    stateText = event.value
                )
            }

            //if title is empty AND user is not entering title, show hint text
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    stateIsHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.stateText.isBlank()
                )
            }
            //updates content view w/ user's input content
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    stateText = event.value
                )
            }

            // if content is empty AND user is not entering content, show hint text
            is AddEditNoteEvent.ChangedContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    stateIsHintVisible = !event.focusState.isFocused &&
                            _noteContent.value.stateText.isBlank()
                )

            }

            is AddEditNoteEvent.ChangeColour -> {
                _noteColour.intValue = event.colour
            }

            is AddEditNoteEvent.SaveNote -> {
                //calls addNote Usecase
                viewModelScope.launch {
                    try {
                        noteUseCases.addNoteUseCase(
                            Note(
                                title = noteTitle.value.stateText,
                                content = noteContent.value.stateText,
                                timestamp = System.currentTimeMillis(),
                                colour = noteColour.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                        // this flags up an entry error if title and content are empty from AddNoteUseCase
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "I...I Couldn't save her john..."
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveNote : UiEvent()

    }

}