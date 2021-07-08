package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.MavericksState
import com.rixspi.domain.util.empty
import com.rixspi.notes.presentation.model.EditableNoteItem2

data class AddNoteViewState2(
    val notes: List<EditableNoteItem2> = listOf(EditableNoteItem2()),
    val added: Boolean = false,
    val command: Boolean = false,
    val activeNote: String = String.empty
) : MavericksState
