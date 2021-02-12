package com.rixspi.notes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rixspi.domain.model.ContentInfo
import com.rixspi.domain.model.Note

val padding = 4.dp

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