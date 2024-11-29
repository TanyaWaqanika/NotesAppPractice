package com.plcoding.notespracticeapp.appStructure.presentation.ui.view.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.notespracticeapp.appStructure.domain.model.Note
import com.plcoding.notespracticeapp.appStructure.presentation.ui.components.OrderSection
import com.plcoding.notespracticeapp.appStructure.presentation.ui.navigation.NoteScreen
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NoteScreen.AddEditNoteScreen.route)
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Notes",
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NoteListEvent.ToggleOrderSection) //toggle order event sent from viewmodel to UI
                    },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
            }
            // slide in/slide out notes animation
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NoteListEvent.Order(it)) // new note order change
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier
                                .fillMaxSize()) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // when individual note is clicked, goes to AddEditNote Screen
                                navController.navigate(
                                    NoteScreen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColour=${note.colour}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(NoteListEvent.DeleteNote(note))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Good note, sweet prince",
                                    actionLabel = "Undo"
                                )
                                // when Undo button is hit within a certain, note is restored
                                // snackbarResult.ActionPerformed facilitates this ^
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NoteListEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    // adds spacing between each note
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit // triggers callback function

) {
    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.colour),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect( // dark colour of flipped note edge
                    color = Color(
                        ColorUtils.blendARGB(note.colour, 0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete note",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}