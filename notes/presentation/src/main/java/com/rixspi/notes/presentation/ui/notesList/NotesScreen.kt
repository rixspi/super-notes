package com.rixspi.notes.presentation.ui.notesList


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.rixspi.domain.util.empty
import com.rixspi.notes.presentation.NotesViewModel

import com.rixspi.notes.presentation.model.ContentInfoListItem
import com.rixspi.notes.presentation.model.NoteListItem


val padding = 4.dp

@Composable
fun NotesScreen(
    goToAddNote: () -> Unit
) {
    val viewModel: NotesViewModel = mavericksViewModel()
    val state = viewModel.collectAsState().value

    Scaffold(
        floatingActionButton = { FabButtonView(goToAddNote) }
    ) {
        // TODO Display no notes view
        state.notes()?.let {
            NotesListView(notes = it, onNoteClick = { viewModel.removeNote(it) })
        }
    }
}

@Composable
fun FabButtonView(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick
    ) {
        // TODO Provide description
        Icon(Icons.Filled.Favorite, contentDescription = String.empty)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteView(
    note: NoteListItem,
    onNoteClick: (NoteListItem) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(padding),
        backgroundColor = Color(note.backgroundColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .combinedClickable(
                onLongClick = {
                    onNoteClick(note)
                },
                onClick = {}
            )

    ) {
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Text(note.title, modifier = Modifier.padding(vertical = padding))
            ContentInfosListView(contentInfos = note.contentInfoListElement)
            Column {
                note.childrenNotes.forEach {
                    NoteView(note = it, onNoteClick = {})
                }
            }
        }
    }
}

fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun NotesListView(
    notes: List<NoteListItem>,
    onNoteClick: (NoteListItem) -> Unit = {}
) {
    LazyColumn {
        items(notes.count(), itemContent = { index ->
            NoteView(notes[index], onNoteClick)
        })
    }
}

@Composable
fun ContentInfoView(content: ContentInfoListItem) {
    Card(
        shape = RoundedCornerShape(padding)
    ) {
        Column(
            modifier = Modifier.padding(padding)
        ) {
            with(content) { Text(text) }
        }
    }
}

@Composable
fun ContentInfosListView(contentInfos: List<ContentInfoListItem>) {
    Column {
        contentInfos.forEach { content ->
            ContentInfoView(content = content)
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = false, backgroundColor = 0xFFFFFFFF
)
@Composable
fun NotePreview() {
    val contentInfos = listOf(
        ContentInfoListItem(id = "1", text = "Content info 1"),
        ContentInfoListItem(id = "2", text = "Content info 2"),
        ContentInfoListItem(id = "3", text = "Content info 3"),
    )
    NoteView(
        note = NoteListItem(
            id = "1",
            title = "Testsass",
            backgroundColor = 0x989a82,
            contentInfoListElement = contentInfos
        )
    )
}

@Preview(
    showBackground = true, showSystemUi = false, backgroundColor = 0xFFFFFFFF
)
@Composable
fun NotesListPreview() {
    val contentInfos = listOf(
        ContentInfoListItem(id = "1", text = "Content info 1"),
        ContentInfoListItem(id = "2", text = "Content info 2"),
        ContentInfoListItem(id = "3", text = "Content info 3"),
    )
    val note1 = NoteListItem(
        id = "1",
        title = "Testsass",
        backgroundColor = 0x989a82,
        contentInfoListElement= contentInfos
    )

    val note2 = NoteListItem(
        id = "2",
        title = "Testsass",
        backgroundColor = 0x989a82,
        contentInfoListElement = contentInfos
    )

    val note3 = NoteListItem(
        id = "3",
        title = "Note with children",
        backgroundColor = 4287617663,
        contentInfoListElement = contentInfos,
        childrenNotes = listOf(note1, note2)
    )

    NotesListView(notes = listOf(note1, note2, note3))
}