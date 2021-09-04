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
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rixspi.common.presentation.ui.collectAsState
import com.rixspi.common.presentation.ui.mavericksViewModel

import com.rixspi.domain.util.empty
import com.rixspi.notes.presentation.NotesViewModel
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
            Text(note.text ?: String.empty, modifier = Modifier.padding(vertical = padding))
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



@Preview(
    showBackground = true, showSystemUi = false, backgroundColor = 0xFFFFFFFF
)
@Composable
fun NotePreview() {

    NoteView(
        note = NoteListItem(
            id = "1",
            title = "Testsass",
            backgroundColor = 0x989a82
        )
    )
}

@Preview(
    showBackground = true, showSystemUi = false, backgroundColor = 0xFFFFFFFF
)
@Composable
fun NotesListPreview() {

    val note1 = NoteListItem(
        id = "1",
        title = "Testsass",
        backgroundColor = 0x989a82
    )

    val note2 = NoteListItem(
        id = "2",
        title = "Testsass",
        backgroundColor = 0x989a82
    )

    val note3 = NoteListItem(
        id = "3",
        title = "Note with children",
        backgroundColor = 4287617663,
        childrenNotes = listOf(note1, note2)
    )

    NotesListView(notes = listOf(note1, note2, note3, note1))
}
