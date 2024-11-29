package com.plcoding.notespracticeapp.appStructure.presentation.ui.view.edit


import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.notespracticeapp.appStructure.domain.model.Note
import com.plcoding.notespracticeapp.appStructure.presentation.ui.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColour: Int, //needed to select associated color w/ note
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val snackbarHostState = remember { SnackbarHostState() }

    // note colour animation is initialised here
    // remember stores the obj initialised and allows it persist across recompositions
    // e.g. survive lifecycle / composition events like screen orientation/pausing
    val noteBackgroundAnimatable = remember {
        Animatable(
            //if noteColour is a valid value, show that colour
            Color(
                if (noteColour != -1) noteColour
                else
                    viewModel.noteColour.value
            ) //if invalid (-1), use colour value from viewModel as backup
        )
    }

    // allows colour animation to run safely and smoothly
    // coroutine and animation will ONLY run while composable is on screen
    // rememberCoroutineScope makes sure animation still works even if cancelled mid-way through
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp() //returns user to note screen AFTER note has been saved
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                // when save button is clicked, event is sent to viewModel to save note
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save Note"
                )
            }
        }
    ) { paddingValues ->
        Column( // row for colour selection
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {// individual circle for each colour available
                Note.noteColours.forEach { color ->
                    val colourInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp) //hardcoded screen size
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColour.value == colourInt) {
                                    Color.Black // when colour is selected, border is black
                                } else Color.Transparent, // when unselected, transparent
                                shape = CircleShape
                            )
                            // when button is clicked, it will start colour change animation
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colourInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColour(colourInt))
                            }

                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Title Hint
            TransparentHintTextField(
                text = titleState.stateText,
                hint = titleState.stateHint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.stateIsHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Content Hint
            TransparentHintTextField(
                text = contentState.stateText,
                hint = contentState.stateHint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangedContentFocus(it))
                },
                isHintVisible = contentState.stateIsHintVisible,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

