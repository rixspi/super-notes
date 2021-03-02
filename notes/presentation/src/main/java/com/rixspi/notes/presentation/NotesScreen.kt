package com.rixspi.notes.presentation


import android.content.Context
import android.widget.Toast
import androidx.activity.contextaware.ContextAware
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

import com.rixspi.common.presentation.mvrx.collectState
import com.rixspi.common.presentation.mvrx.mavericksViewModel
import com.rixspi.domain.model.ContentInfo
import com.rixspi.domain.model.Note


val padding = 4.dp

@Composable
fun NotesScreen(
    goToAddNote: () -> Unit
) {
    val viewModel: NotesViewModel = mavericksViewModel()
    val state = viewModel.collectState()

    Scaffold(
        floatingActionButton = { Fab(goToAddNote) }
    ) {
        // TODO Display no notes view
        state.notes()?.invoke()?.let {
            NotesList(notes = it, onNoteClick = { viewModel.removeNote(it) })
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Note(
    note: Note,
    onNoteClick: (Note) -> Unit = {}
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
            ContentInfosList(contentInfos = note.contentInfos)
            Column {
                note.childrenNotes.forEach {
                    Note(note = it, onNoteClick = {})
                }
            }
        }
    }
}
fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun NotesList(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit = {}
) {
    LazyColumn {
        items(notes.count(), itemContent = { index ->
            Note(notes[index], onNoteClick)
        })
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
    Column() {
        contentInfos.forEach { content ->
            ContentInfo(content = content)
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = false, backgroundColor = 0xFFFFFFFF
)
@Composable
fun NotePreview() {
    val contentInfos = listOf(
        ContentInfo(text = "Content info 1"),
        ContentInfo(text = "Content info 2"),
        ContentInfo(text = "Content info 3"),
    )
    Note(
        note = Note(
            title = "Testsass",
            backgroundColor = 0x989a82,
            contentInfos = contentInfos
        )
    )
}

@Preview(
    showBackground = true, showSystemUi = false, backgroundColor = 0xFFFFFFFF
)
@Composable
fun NotesListPreview() {
    val contentInfos = listOf(
        ContentInfo(text = "Content info 1"),
        ContentInfo(text = "Content info 2"),
        ContentInfo(text = "Content info 3"),
    )
    val note1 = Note(
        title = "Testsass",
        backgroundColor = 0x989a82,
        contentInfos = contentInfos
    )

    val note2 = Note(
        title = "Testsass",
        backgroundColor = 0x989a82,
        contentInfos = contentInfos
    )

    val note3 = Note(
        title = "Note with children",
        backgroundColor = 4287617663,
        contentInfos = contentInfos,
        childrenNotes = listOf(note1, note2)
    )

    NotesList(notes = listOf(note1, note2, note3))
}