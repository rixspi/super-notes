package com.rixspi.notes.presentation.mapper

import com.rixspi.common.domain.model.Note
import com.rixspi.notes.presentation.model.EditableNoteItem

fun EditableNoteItem.toNote(): Note = Note(
    id = id,
    parentId = parentId,
    depth = depth,
    backgroundColor = backgroundColor,
    backgroundImage = backgroundImage,
    title = title,
    childrenNotes = emptyList(),
)
