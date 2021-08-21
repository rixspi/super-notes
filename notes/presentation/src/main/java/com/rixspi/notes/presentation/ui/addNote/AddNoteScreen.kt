package com.rixspi.notes.presentation.ui.addNote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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


    if (state.value.added) {
        noteAdded()
    }

    NoteView(
        createNote = { viewModel.createNote() },
        addNote = { viewModel.addNoteTemp() },
        addContent = { viewModel.addContentTemp() },
        setActiveNote = { viewModel.setActiveNote(it) },
        updateTitle = { id, title -> viewModel.updateTitle(id, title) },
        state = state.value
    )
}

@Composable
private fun NoteView(
    createNote: () -> Unit,
    addNote: () -> Unit,
    addContent: () -> Unit,
    setActiveNote: (String) -> Unit,
    updateTitle: (String, String) -> Unit,
    state: AddNoteViewState2
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = { FabButtonView { createNote() } },
        bottomBar = {
            if (state.activeNote != String.empty) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Button(onClick = {}) {
                        Text(text = "Image")
                    }
                    Button(onClick = {
                        addNote()
                    }) {
                        Text(text = "Container")
                    }
                    Button(onClick = {
                        addContent()
                    }) {
                        Text(text = "Text")
                    }
                }
            }
        }
    ) {
        Column(Modifier.padding(it)) {
            NoteEditor(state.notes, updateCurrentFocusNote = { setActiveNote(it) }) { noteId, title ->
                updateTitle(noteId, title)
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
    // TODO Scroll to the added element
    //  probably I need to diff the notes list in the view model and send the updated index here
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        notes.forEach { note ->
            item {
                TextInput(
                    modifier = Modifier
                        .background(MaterialTheme.colors.secondary)
                        .padding(start = note.depth.dp * 4)
                        .background(MaterialTheme.colors.background),
                    label = "Title",
                    text = note.title,
                    onFocusChange = {
                        if (it) {
                            updateCurrentFocusNote(note.id)
                        }
                    }) {
                    updateTitle(note.id, it)
                }
            }
            note.contentInfos.forEach { content ->
                item {
                    TextInput(label = "Content", text = content.text ?: String.empty, onFocusChange = {
                        if (it) {
                            updateCurrentFocusNote(note.id)
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    label: String,
    text: String = String.empty,
    onFocusChange: (Boolean) -> Unit = {},
    onChange: (String) -> Unit = {},
) {
    val textState = remember { mutableStateOf(TextFieldValue(text)) }

    TextField(
        modifier = modifier
            .focusTarget()
            .onFocusChanged { onFocusChange(it.isFocused) },
        shape = shapes.large,
        value = textState.value,
        onValueChange = {
            textState.value = it
            onChange(it.text)
        },
        label = { Text(text = label) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent,
        )
    )
}

@Preview(
    showBackground = true, showSystemUi = false, backgroundColor = 0xFFFFFFFF
)
@Composable
fun NoteScreenPreview() {
    NoteView(
        createNote = { },
        addNote = { },
        addContent = { },
        setActiveNote = {},
        updateTitle = { _, _ -> },
        state = AddNoteViewState2(
            notes = listOf(
                EditableNoteItem2(depth = 0),
                EditableNoteItem2(depth = 1),
                EditableNoteItem2(depth = 2),
                EditableNoteItem2(depth = 3),
            ),
            activeNote = "1"
        )
    )
}
