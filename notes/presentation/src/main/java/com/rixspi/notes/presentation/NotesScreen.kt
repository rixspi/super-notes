package com.rixspi.notes.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

import com.rixspi.common.presentation.mvrx.collectState
import com.rixspi.common.presentation.mvrx.mavericksViewModel
import com.rixspi.domain.fold
import com.rixspi.domain.model.ContentInfo
import com.rixspi.domain.model.Note


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
                LazyColumn {
                    items(it.count(), itemContent = { index ->
                        Note(it[index])
                    })
                }
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


val padding = 4.dp

@Preview(
    showBackground = true
)
@Composable
fun NotePreview() {
    Note(note = Note(title = "Testsass", backgroundColor = 0x989a82))
}

@Composable
fun Note(note: Note) {
    Card(
        shape = RoundedCornerShape(padding),
        backgroundColor = Color(note.backgroundColor)
    ) {
        Column {
            Text(note.title, modifier = Modifier.padding(vertical = padding))
            ContentInfosList(contentInfos = note.contentInfos)
        }
    }
}

@Composable
fun NotesList(notes: List<Note>) {
    notes.forEach { note ->
        Note(note)
        NotesList(notes = note.childrenNotes)
    }
}

@Composable
fun ContentInfo(content: ContentInfo) {
    Card(
        shape = RoundedCornerShape(padding)
    ) {
        Column(
            modifier = Modifier.padding(padding)
        ) {
            with(content) {
                text?.let { Text(it) }
            }
        }
    }
}

@Composable
fun ContentInfosList(contentInfos: List<ContentInfo>) {
    contentInfos.forEach { content ->
        ContentInfo(content = content)
    }
}