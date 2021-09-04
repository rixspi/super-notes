package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.MavericksState
import com.rixspi.domain.util.empty
import com.rixspi.notes.presentation.model.EditableNoteItem

data class AddNoteViewState(
    val notes: List<EditableNoteItem> = listOf(EditableNoteItem()),
    val added: Boolean = false,
    val command: Boolean = false,
    val activeNote: String = String.empty
) : MavericksState
