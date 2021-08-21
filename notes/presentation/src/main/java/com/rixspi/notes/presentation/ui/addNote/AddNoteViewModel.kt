package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.MavericksViewModelFactory
import com.rixspi.common.domain.interactors.CreateNote
import com.rixspi.common.framework.di.AssistedViewModelFactory
import com.rixspi.common.framework.di.hiltMavericksViewModelFactory
import com.rixspi.common.presentation.BaseViewModel
import com.rixspi.domain.util.empty
import com.rixspi.notes.presentation.model.EditableNoteItem
import com.rixspi.notes.presentation.model.EditableNoteItem2
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.UUID

class AddNoteViewModel @AssistedInject constructor(
    @Assisted state: AddNoteViewState2,
    private val createNote: CreateNote
) : BaseViewModel<AddNoteViewState2>(state) {

    private val firstNote: EditableNoteItem2
    private val notesHandler: NotesHandler = NotesHandler().also {
        firstNote = state.notes.first()
        it.appendNote(firstNote)
    }

    fun setActiveNote(noteId: String) {
        setState {
            copy(activeNote = noteId)
        }
    }

    fun addNote(parentNote: EditableNoteItem2) {
        val id = UUID.randomUUID().toString()
        notesHandler.appendNote(EditableNoteItem2(id = id, parentId = parentNote.id))
        updateNotes()
    }

    fun addNoteTemp() {
        val id = UUID.randomUUID().toString()
        withState { state ->
            if (state.activeNote == String.empty) {
                //error
            } else {
                notesHandler.appendNote(EditableNoteItem2(id = id, parentId = state.activeNote))
                updateNotes()
            }
        }
    }

    fun removeNote(noteId: String = String.empty) {
        withState { state ->
            notesHandler.removeNote(state.activeNote)
            updateNotes()
        }
    }

    fun updateTitle(noteId: String, title: String) {
        notesHandler.setTitle(noteId, title)
        updateNotes()
    }

    fun addContent(note: EditableNoteItem, index: Int) {
        val id = UUID.randomUUID().toString()
        updateNotes()
    }

    fun addContentTemp(index: Int = 0) {
        val id = UUID.randomUUID().toString()
        withState { state ->
            if (state.activeNote == String.empty) {
                //error
            } else {
                notesHandler.addContentInfo(state.activeNote, id)
                updateNotes()
            }
        }
    }

    private fun updateNotes() {
        setState {
            copy(notes = notesHandler.getOrderedList())
        }
    }

    fun updateContentInfo(note: EditableNoteItem, index: Int, text: String) = {}
    //setState { updateContentInfo(note = note, index = index, text = text) }

    fun removeContentInfo(note: EditableNoteItem, index: Int) = {}
    // setState { removeContentInfo(note, index) }

    fun createNote() = withState { state ->
        // This needs some more work
        // createNote.execute(
        //     params = CreateNote.Params(state.note.toNote())
        // ) {
        //     // TODO handle errors
        //     // copy(added = true)
        //     copy()
        // }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<AddNoteViewModel, AddNoteViewState2> {
        override fun create(state: AddNoteViewState2): AddNoteViewModel
    }

    companion object :
        MavericksViewModelFactory<AddNoteViewModel, AddNoteViewState2> by hiltMavericksViewModelFactory()
}
