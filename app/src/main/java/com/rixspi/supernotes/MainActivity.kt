package com.rixspi.supernotes

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rixspi.common.presentation.ui.BackgroundDayAndNightView
import com.rixspi.common.presentation.ui.styling.SuperNoteTheme
import com.rixspi.notes.presentation.ui.addNote.AddNoteScreen
import com.rixspi.notes.presentation.ui.notesList.NotesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SuperNoteTheme {
                BackgroundDayAndNightView {
                    NavHost(navController = navController, startDestination = "notes") {
                        composable("notes") {
                            NotesScreen { navController.navigate("addNote") }
                        }
                        composable("addNote") {
                            AddNoteScreen { navController.navigateUp() }
                        }
                    }
                }
            }
        }
    }
}
