package com.rixspi.notes.presentation.ui.addNote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.rixspi.common.presentation.ui.styling.shapes
import com.rixspi.domain.util.empty
import com.rixspi.notes.presentation.ui.notesList.FabButtonView

@Composable
fun SnackbarFUckingShit(
    host: SnackbarHostState
) {
    SnackbarHost(hostState = host) { data ->
        Snackbar(
            action = {
                Button(onClick = {}) {
                    Text("MyAction")
                }
            },
            modifier = Modifier.padding(8.dp)
        ) { Text(text = "This is a snackbar!") }
    }
}

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
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FabButtonView {
                viewModel.createNote()
            }
        }
    ) {
        Column {
            NoteEditor(viewModel)
            Row {
                Button(onClick = {}) {
                    Text(text = "Image")
                }
                Button(onClick = {  }) {
                    Text(text = "Container")
                }
                Button(onClick = {  }) {
                    Text(text = "Text")
                }
            }
        }
    }
}

@Composable
fun NoteEditor(viewModel: AddNoteViewModel) {
    Column() {
        TextInput(label = "Title")
        TextInput(label = "Content") { text ->
            if (text.length > 1) {
                val lastTwoChars = text.substring(text.length - 2, text.length)
                if (lastTwoChars.contains("  ")) {
                    viewModel.command(true)
                } else {
                    viewModel.command(false)
                }
            }
        }
    }
}

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    label: String,
    initText: String = String.empty,
    onChange: (String) -> Unit = {}
) {
    val (text, setText) = remember { mutableStateOf(TextFieldValue(initText)) }
    TextField(
        modifier = modifier,
        shape = shapes.large,
        value = text,
        onValueChange = {
            setText(it)
            onChange(it.text)
        },
        placeholder = { Text(label) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
        )
    )
}
