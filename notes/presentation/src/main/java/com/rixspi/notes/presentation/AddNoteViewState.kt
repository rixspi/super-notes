package com.rixspi.notes.presentation

import com.airbnb.mvrx.MavericksState
import com.rixspi.common.domain.model.Note

data class AddNoteViewState(
    val note: Note = Note(contentInfos = listOf(com.rixspi.common.domain.model.ContentInfo()))
) : MavericksState {
    fun setTitle(title: String): AddNoteViewState = copy(note = note.copy(title = title))

    fun addContentInfo(): AddNoteViewState =
        copy(note = note.copy(contentInfos = note.contentInfos + com.rixspi.common.domain.model.ContentInfo()))

    fun updateContentInfo(index: Int, text: String): AddNoteViewState {
        val modifiedContentList = note.contentInfos.toMutableList()
            .apply {
                if (index < note.contentInfos.lastIndex) {
                    this[index] = com.rixspi.common.domain.model.ContentInfo(text = text)
                }
            }.toList()
        return copy(note = note.copy(contentInfos = modifiedContentList))
    }

    fun removeContentInfo(): AddNoteViewState =
        copy(
            note = note.copy(
                contentInfos = note.contentInfos.toMutableList().apply { removeLast() })
        )

    fun addNote(note: Note): AddNoteViewState =
        copy(note = note.copy(childrenNotes = note.childrenNotes + note))

    fun remoteNote(note: Note): AddNoteViewState =
        copy(note = note.copy(childrenNotes = note.childrenNotes - note))
}