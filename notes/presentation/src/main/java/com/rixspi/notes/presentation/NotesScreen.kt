package com.rixspi.notes.presentation

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.imageResource
import com.airbnb.mvrx.InternalMavericksApi
import com.rixspi.common.presentation.mvrx.collectState
import com.rixspi.common.presentation.mvrx.mavericksViewModel
import com.rixspi.domain.fold


@OptIn(InternalMavericksApi::class)
@Composable
fun NotesScreen(
    addNote: () -> Unit
) {
    val viewModel: NotesViewModel = mavericksViewModel()
    val state = viewModel.collectState()

    Scaffold(
        floatingActionButton = { Fab(addNote) }
    ) {
        state.notes()?.let { notes ->
            notes.fold(error = {}, success = {
                NotesList(notes = it)
            })
        }
    }
}

@Composable
fun Fab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(Icons.Filled.Favorite, contentDescription = "")
    }
}