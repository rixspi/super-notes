package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.MavericksState
import com.rixspi.notes.presentation.model.EditableNoteItem2

data class AddNoteViewState2(
    val notes: List<EditableNoteItem2> = listOf(EditableNoteItem2()),
    val added: Boolean = false,
    val command: Boolean = false,
    private val notesHandler: NotesHandler = NotesHandler().also { it.appendNote(notes.first()) }
) : MavericksState {

    private fun getRootNote() = notes.first()

    fun setTitle(noteId: String, title: String): AddNoteViewState2 {
        notesHandler.setTitle(noteId, title)
        return prepareNewState()
    }

    fun addContentInfo(
        note: EditableNoteItem2 = getRootNote(),
        id: String,
        index: Int = 0
    ): AddNoteViewState2 {

        return prepareNewState()
    }

    fun updateContentInfo(
        note: EditableNoteItem2 = getRootNote(),
        index: Int,
        text: String,
    ): AddNoteViewState2 {

        return prepareNewState()
    }

    fun removeContentInfo(
        note: EditableNoteItem2 = getRootNote(),
        index: Int = 0
    ): AddNoteViewState2 {

        return prepareNewState()
    }

    fun addChildrenNote(
        parentNote: EditableNoteItem2 = getRootNote(),
        id: String
    ): AddNoteViewState2 {

        // TODO Adding notes after first results in creating a children and then all next are inside this first one
        //  investigate ;)

        notesHandler.appendNote(EditableNoteItem2(id = id, parentId = parentNote.id))
        return prepareNewState()
    }

    fun removeChildrenNote(
        noteId: String
    ): AddNoteViewState2 {
        notesHandler.removeNote(noteId)
        return prepareNewState()
    }

    private fun prepareNewState(): AddNoteViewState2 = copy(notes = notesHandler.getOrderedList())
}
