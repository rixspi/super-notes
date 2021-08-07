package com.rixspi.notes.presentation.ui.addNote

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.rixspi.common.presentation.ui.collectAsState
import com.rixspi.common.presentation.ui.mavericksViewModel
import com.rixspi.common.presentation.ui.styling.shapes
import com.rixspi.domain.util.empty
import com.rixspi.notes.presentation.model.EditableNoteItem2
import com.rixspi.notes.presentation.ui.notesList.FabButtonView

@Composable
fun AddNoteScreen(
    noteAdded: () -> Unit
) {
    val viewModel: AddNoteViewModel = mavericksViewModel()
    val state = viewModel.collectAsState()

    val note = state.value.notes

    if (state.value.added) {
        noteAdded()
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FabButtonView {
                viewModel.createNote()
            }
        },
        bottomBar = {
            if (state.value.activeNote != String.empty) {
                Row {
                    Button(onClick = {}) {
                        Text(text = "Image")
                    }
                    Button(onClick = {
                        viewModel.addNoteTemp()
                    }) {
                        Text(text = "Container")
                    }
                    Button(onClick = {
                        viewModel.addContentTemp()
                    }) {
                        Text(text = "Text")
                    }
                }
            }
        }
    ) {
        Column {
            NoteEditor(state.value.notes, updateCurrentFocusNote = { viewModel.setActiveNote(it) }) { noteId, title ->
                viewModel.updateTitle(noteId, title)
            }
        }
    }
}

@Composable
fun NoteEditor(
    notes: List<EditableNoteItem2>,
    updateCurrentFocusNote: (String) -> Unit,
    updateTitle: (String, String) -> Unit
) {
    Column {
        notes.forEach { note ->

            Log.e("LOL", note.toString())

            TextInput(label = "Titl2e", text = note.title, onFocusChange = {
                if (it) {
                    updateCurrentFocusNote(note.id)
                }
            }) {
                //updateTitle(note.id, it)
            }
            // This displays only "title" after adding a container after content info
            //  something I made is very wrong
            note.contentInfos.forEach { content ->
                Log.e("LOL2", content.toString())
                TextInput(label = "Content", text = content.text ?: String.empty, onFocusChange = {
                    if (it) {
                        updateCurrentFocusNote(note.id)
                    }
                })
            }
        }
    }
}

@Composable
// This displays wrong label
fun TextInput(
    modifier: Modifier = Modifier,
    label: String,
    text: String = String.empty,
    onFocusChange: (Boolean) -> Unit = {},
    onChange: (String) -> Unit = {},
) {
    val textState = remember { mutableStateOf(TextFieldValue(text)) }

    Log.e("LOL3", label)
    // result of above log is correct, textField still displays wrong text
    TextField(
        modifier = modifier
            .focusModifier()
            .onFocusChanged { onFocusChange(it.isFocused) },
        shape = shapes.large,
        value = textState.value,
        onValueChange = {
            textState.value = it
            onChange(it.text)
        },
        label = {  Text(text = label) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
        )
    )
}
