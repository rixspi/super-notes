package com.rixspi.notes.presentation.mapper

import com.rixspi.common.domain.model.Note
import com.rixspi.notes.presentation.model.NoteListItem

fun mapNote(
    input: Note
): NoteListItem = with(input) {
    NoteListItem(
        id = id,
        backgroundColor = backgroundColor,
        backgroundImage = backgroundImage,
        title = title,
        childrenNotes = childrenNotes.map { mapNote(it) },
    )
}

fun mapNotesList(
    input: List<Note>
): List<NoteListItem> = input.map {
    mapNote(it)
}
