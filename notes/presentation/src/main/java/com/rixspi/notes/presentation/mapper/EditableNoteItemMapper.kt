package com.rixspi.notes.presentation.mapper

import com.rixspi.common.domain.model.Note
import com.rixspi.domain.util.empty
import com.rixspi.notes.presentation.model.EditableNoteItem
import kotlinx.serialization.SerialName
import java.util.*

fun EditableNoteItem.toNote(): Note = Note(
    id = id,
    backgroundColor = backgroundColor,
    backgroundImage = backgroundImage,
    title = title,
    childrenNotes = childrenNotes.map { it.toNote() },
    contentInfos = contentInfos.map { it.toContentInfo() }
)
