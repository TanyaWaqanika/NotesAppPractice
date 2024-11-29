package com.plcoding.notespracticeapp.appStructure.presentation.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.notespracticeapp.appStructure.presentation.ui.view.edit.AddEditNoteScreen
import com.plcoding.notespracticeapp.appStructure.presentation.ui.view.list.NoteListScreen

// controls and organises the routes b/w different screens/activities
@ExperimentalAnimationApi
@Composable
fun NoteNavigation(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NoteScreen.NotesScreen.route
    ) {
        composable(route = NoteScreen.NotesScreen.route) {
            NoteListScreen(navController = navController)
        }
        composable(
            route = NoteScreen.AddEditNoteScreen.route + "?noteId={noteId}&noteColour={noteColour}",
            arguments = listOf(
                navArgument(
                    name = "noteId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "noteColour"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
            )
        ) {
            val colour = it.arguments?.getInt("noteColour") ?: -1
            AddEditNoteScreen(
                navController = navController,
                noteColour = colour
            )
        }
    }
}