package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.MavericksState
import com.rixspi.common.domain.model.ContentInfo
import com.rixspi.common.domain.model.Note
import com.rixspi.notes.presentation.model.EditableContentInfoItem
import com.rixspi.notes.presentation.model.EditableNoteItem

data class AddNoteViewState(
    val note: EditableNoteItem = EditableNoteItem(contentInfos = listOf(EditableContentInfoItem())),
    val added: Boolean = false
) : MavericksState {
    fun setTitle(title: String): AddNoteViewState = copy(note = note.copy(title = title))

    fun addContentInfo(
        note: EditableNoteItem = this.note,
        indexOfCurrentContentInfo: Int = 0
    ): AddNoteViewState {
        val modifiedContentInfos = note.contentInfos
            .toMutableList()
            .apply { add(indexOfCurrentContentInfo, EditableContentInfoItem()) }
            .toList()

        val modifiedNote = note.copy(contentInfos = modifiedContentInfos)

        return if (note == this.note) {
            copy(note = modifiedNote)
        } else {
            val modifiedChildrenNotes = this.note.childrenNotes
                .toMutableList()
                .apply { set(indexOfFirst { it.id == note.id }, modifiedNote) }
                .toList()

            copy(note = this.note.copy(childrenNotes = modifiedChildrenNotes))
        }
    }

    fun updateContentInfo(
        note: EditableNoteItem = this.note,
        index: Int, text:
        String
    ): AddNoteViewState {
        val modifiedContentInfos = note.contentInfos
            .toMutableList()
            .apply {
                if (index <= note.contentInfos.lastIndex) {
                    this[index] = EditableContentInfoItem(text = text)
                }
            }
            .toList()

        val modifiedNote = note.copy(contentInfos = modifiedContentInfos)

        return if (note == this.note) {
            copy(note = modifiedNote)
        } else {
            val modifiedChildrenNotes = this.note.childrenNotes
                .toMutableList()
                .apply { set(indexOfFirst { it.id == note.id }, modifiedNote) }
                .toList()

            copy(note = this.note.copy(childrenNotes = modifiedChildrenNotes))
        }
    }

    fun removeContentInfo(): AddNoteViewState =
        copy(
            note = note.copy(
                contentInfos = note.contentInfos
                    .toMutableList()
                    .apply { removeLast() })
        )

    // TODO Add empty note, this should get note where the new one will be added not a new note ;)
    fun addChildrenNote(newNote: EditableNoteItem): AddNoteViewState =
        copy(note = note.copy(childrenNotes = note.childrenNotes + newNote))

    fun removeChildrenNote(noteToRemove: EditableNoteItem): AddNoteViewState =
        copy(note = note.copy(childrenNotes = note.childrenNotes - noteToRemove))
}
