package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.MavericksState
import com.rixspi.notes.presentation.model.EditableContentInfoItem
import com.rixspi.notes.presentation.model.EditableNoteItem
import java.util.*

data class AddNoteViewState(
    val note: EditableNoteItem = EditableNoteItem(
        id = UUID.randomUUID().toString(),
        contentInfos = listOf(EditableContentInfoItem())
    ),
    val added: Boolean = false
) : MavericksState {

    fun setTitle(note: EditableNoteItem = this.note, title: String): AddNoteViewState {
        val modifiedNote = note.copy(title = title)

        return prepareNewState(note, modifiedNote)
    }

    fun addContentInfo(
        note: EditableNoteItem = this.note,
        id: String,
        index: Int = 0
    ): AddNoteViewState {
        val modifiedContentInfos = note.contentInfos.modify {
            add(index, EditableContentInfoItem(id = id))
        }

        val modifiedNote = note.copy(contentInfos = modifiedContentInfos)

        return prepareNewState(note, modifiedNote)
    }

    fun updateContentInfo(
        note: EditableNoteItem = this.note,
        index: Int,
        text: String,
    ): AddNoteViewState {
        val modifiedContentInfos = note.contentInfos.modify {
            if (index <= note.contentInfos.lastIndex) {
                this[index] = this[index].copy(text = text)
            }
        }

        val modifiedNote = note.copy(contentInfos = modifiedContentInfos)

        return prepareNewState(note, modifiedNote)
    }

    fun removeContentInfo(
        note: EditableNoteItem = this.note,
        index: Int = 0
    ): AddNoteViewState {
        val modifiedContentInfos = note.contentInfos.modify {
            if (index <= note.contentInfos.lastIndex) {
                removeAt(index)
            }
        }

        val modifiedNote = note.copy(contentInfos = modifiedContentInfos)

        return prepareNewState(note, modifiedNote)
    }

    fun addChildrenNote(
        note: EditableNoteItem = this.note,
        id: String,
        index: Int = 0
    ): AddNoteViewState {
        val modifiedChildrenNotes = note.childrenNotes.modify {
            add(
                index,
                EditableNoteItem(id = id, contentInfos = listOf(EditableContentInfoItem(id = id)))
            )
        }

        val modifiedNote = note.copy(childrenNotes = modifiedChildrenNotes)

        return prepareNewState(note, modifiedNote)
    }

    fun removeChildrenNote(
        note: EditableNoteItem = this.note,
        index: Int = 0
    ): AddNoteViewState {
        val modifiedChildrenNotes = note.childrenNotes.modify {
            if (index <= note.childrenNotes.lastIndex) {
                removeAt(index)
            }
        }

        val modifiedNote = note.copy(childrenNotes = modifiedChildrenNotes)

        return prepareNewState(note, modifiedNote)
    }

    private fun <T> List<T>.modify(
        action: MutableList<T>.() -> Unit
    ): List<T> = this
        .toMutableList()
        .apply {
            action(this)
        }
        .toList()


    private fun prepareNewState(
        note: EditableNoteItem,
        modifiedNote: EditableNoteItem
    ): AddNoteViewState {
        // TODO This check fails sometimes
        return if (note.id == this.note.id) {
            copy(note = modifiedNote)
        } else {
            val modifiedChildrenNotes = this.note.childrenNotes.modify {
                set(indexOfFirst { it.id == note.id }, modifiedNote)
            }

            copy(note = this.note.copy(childrenNotes = modifiedChildrenNotes))
        }
    }
}
