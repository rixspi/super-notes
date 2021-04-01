package com.rixspi.notes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.rixspi.common.domain.model.Note
import com.rixspi.common.presentation.ui.styling.shapes
import com.rixspi.domain.util.empty


@Composable
fun AddNoteScreen(
    noteAdded: () -> Unit
) {
    val viewModel: AddNoteViewModel = mavericksViewModel()
    val state = viewModel.collectAsState()

    val note = state.value.note

    if (state.value.added) {
        noteAdded()
    }

    Scaffold(
        floatingActionButton = {
            FabButtonView {
                viewModel.createNote()
            }
        }
    ) {
        NoteEditor(viewModel = viewModel, note = note)

    }
}

@Composable
fun NoteEditor(
    viewModel: AddNoteViewModel,
    note: Note
) {
    LazyColumn {
        item {
            Row {
                TextInput(initText = note.title, label = "Note title") {
                    viewModel.updateTitle(title = it)
                }
                IconButton(
                    onClick = { // this causes out of memory xD viewModel.addNote()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                        "contentDescription",
                        tint = Color.Magenta
                    )
                }
            }
        }
        items(count = note.contentInfos.size, itemContent = { index ->
            Row {
                val contentInfo = note.contentInfos[index].text ?: String.empty
                TextInput(initText = contentInfo, label = "Note content") {
                    viewModel.updateContentInfo(index = index, text = it)
                }

                ContentInfoButtons(
                    add = { viewModel.addContent() },
                    remove = { viewModel.removeContentInfo() }
                )
            }
        })
    }

    note.childrenNotes.forEach {
        NoteEditor(viewModel = viewModel, note = note)
    }
}

@Composable
fun ContentInfoButtons(
    add: () -> Unit,
    remove: () -> Unit
) {
    Row {
        Spacer(Modifier.width(2.dp))
        IconButton(
            onClick = { add() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                "contentDescription",
                tint = Color.Magenta
            )
        }
        Spacer(Modifier.width(2.dp))
        IconButton(
            onClick = { remove() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                "contentDescription",
                tint = Color.Magenta
            )
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