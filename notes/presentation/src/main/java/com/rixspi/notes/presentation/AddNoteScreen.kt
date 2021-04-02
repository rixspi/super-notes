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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.rixspi.common.domain.model.ContentInfo
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
        NoteEditor(
            note = note,
            addNote = { viewModel.addNote() },
            updateTitle = { viewModel.updateTitle(it) },
            updateContentInto = { index, content -> viewModel.updateContentInfo(index, content) },
            addContentInfo = { viewModel.addContent() },
            removeContentInfo = { viewModel.removeContentInfo() }
        )
    }
}

@Composable
fun NoteEditor(
    addNote: () -> Unit,
    updateTitle: (String) -> Unit,
    updateContentInto: (Int, String) -> Unit,
    addContentInfo: () -> Unit,
    removeContentInfo: (Int) -> Unit,
    note: Note
) {
    Column {
        LazyColumn {
            item {
                Row {
                    TextInput(initText = note.title, label = "Note title") {
                        updateTitle(it)
                    }
                    IconButton(
                        onClick = { addNote() }
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
                        updateContentInto(index, it)
                    }

                    ContentInfoButtons(
                        add = { addContentInfo() },
                        remove = { removeContentInfo(index) }
                    )
                }
            })
        }

        note.childrenNotes.forEach {
            NoteEditor(
                note = it,
                addNote = { addNote() },
                updateTitle = { updateTitle(it) },
                updateContentInto = { index, content -> updateContentInto(index, content) },
                addContentInfo = { addContentInfo() },
                removeContentInfo = { removeContentInfo(it) }
            )
        }
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

@Preview
@Composable
fun NotesEditorPreview() {
    val subnoteSubnote = Note(
        contentInfos = listOf(
            ContentInfo()
        )
    )

    val subnote = Note(
        contentInfos = listOf(
            ContentInfo(), ContentInfo()
        ),
        childrenNotes = listOf(subnoteSubnote)
    )


    val note = Note(
        contentInfos = listOf(
            ContentInfo(),
            ContentInfo(),
        ), childrenNotes = listOf(
            subnote, Note()
        )
    )

    Scaffold(
        floatingActionButton = {
            FabButtonView {

            }
        }
    ) {
        NoteEditor(
            addNote = {},
            updateTitle = {},
            updateContentInto = { _, _ -> },
            addContentInfo = { },
            removeContentInfo = { },
            note = note
        )
    }
}
