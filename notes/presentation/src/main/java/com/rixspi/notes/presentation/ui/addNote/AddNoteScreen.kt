package com.rixspi.notes.presentation.ui.addNote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import com.rixspi.common.presentation.ui.styling.shapes
import com.rixspi.domain.util.empty
import com.rixspi.notes.presentation.R
import com.rixspi.notes.presentation.model.EditableContentInfoItem
import com.rixspi.notes.presentation.model.EditableNoteItem
import com.rixspi.notes.presentation.ui.notesList.FabButtonView
import java.util.UUID

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
            addNote = { parent, index -> viewModel.addNote(parent, index) },
            updateTitle = { note, title -> viewModel.updateTitle(note, title) },
            updateContentInto = { note, index, content ->
                viewModel.updateContentInfo(
                    note,
                    index,
                    content
                )
            },
            addContentInfo = { note, index -> viewModel.addContent(note, index) },
            removeContentInfo = { note, index -> viewModel.removeContentInfo(note, index) }
        )
    }
}

@Composable
@Suppress("LongParameterList")
fun NoteEditor(
    addNote: (EditableNoteItem, Int) -> Unit,
    updateTitle: (EditableNoteItem, String) -> Unit,
    updateContentInto: (EditableNoteItem, Int, String) -> Unit,
    addContentInfo: (EditableNoteItem, Int) -> Unit,
    removeContentInfo: (EditableNoteItem, Int) -> Unit,
    note: EditableNoteItem
) {
    Column {
        LazyColumn {
            item {
                Row {
                    TextInput(initText = note.title, label = "Note title") {
                        updateTitle(note, it)
                    }

                    ContentInfoButtons(
                        showRemove = false,
                        add = { addNote(note, 0) },
                    )
                }
            }
            items(count = note.contentInfos.size, itemContent = { index ->
                Row {
                    val contentInfo = note.contentInfos[index].text ?: String.empty
                    TextInput(initText = contentInfo, label = "Note content") {
                        updateContentInto(note, index, it)
                    }

                    ContentInfoButtons(
                        add = { addContentInfo(note, index) },
                        remove = { removeContentInfo(note, index) }
                    )
                }
            })
        }

        note.childrenNotes.forEach {
            NoteEditor(
                note = it,
                addNote = { parentNote, index -> addNote(parentNote, index) },
                updateTitle = { note, title -> updateTitle(note, title) },
                updateContentInto = { note, index, content ->
                    updateContentInto(
                        note,
                        index,
                        content
                    )
                },
                addContentInfo = { note, index -> addContentInfo(note, index) },
                removeContentInfo = { note, index -> removeContentInfo(note, index) }
            )
        }
    }
}

@Composable
fun ContentInfoButtons(
    showAdd: Boolean = true,
    showRemove: Boolean = true,
    add: () -> Unit = {},
    remove: () -> Unit = {}
) {
    Row {

        if (showAdd) {
            Spacer(Modifier.width(2.dp))
            IconButton(onClick = { add() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    "contentDescription",
                    tint = Color.Magenta
                )
            }
        }
        if (showRemove) {
            Spacer(Modifier.width(2.dp))
            IconButton(onClick = { remove() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                    "contentDescription",
                    tint = Color.Magenta
                )
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

@Preview
@Composable
fun NotesEditorPreview() {
    val subnoteSubnote = EditableNoteItem(
        id = UUID.randomUUID().toString(),
        contentInfos = listOf(
            EditableContentInfoItem()
        )
    )

    val subnote = EditableNoteItem(
        id = UUID.randomUUID().toString(),
        contentInfos = listOf(
            EditableContentInfoItem(), EditableContentInfoItem()
        ),
        childrenNotes = listOf(subnoteSubnote)
    )

    val note = EditableNoteItem(
        id = UUID.randomUUID().toString(),
        contentInfos = listOf(
            EditableContentInfoItem(),
            EditableContentInfoItem()
        ), childrenNotes = listOf(
            subnote,
            EditableNoteItem(id = UUID.randomUUID().toString())
        )
    )

    Scaffold(
        floatingActionButton = {
            FabButtonView {}
        }
    ) {
        NoteEditor(
            addNote = { _, _ -> },
            updateTitle = { _, _ -> },
            updateContentInto = { _, _, _ -> },
            addContentInfo = { _, _ -> },
            removeContentInfo = { _, _ -> },
            note = note
        )
    }
}
