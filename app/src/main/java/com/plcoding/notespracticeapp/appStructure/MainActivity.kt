package com.plcoding.notespracticeapp.appStructure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.plcoding.notespracticeapp.appStructure.presentation.ui.navigation.NoteNavigation
import com.plcoding.notespracticeapp.appStructure.presentation.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

// this is needed in main for hilt injection to work
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureNoteAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    paddingValues ->
                    NoteNavigation(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
            }
        }
    }
}
