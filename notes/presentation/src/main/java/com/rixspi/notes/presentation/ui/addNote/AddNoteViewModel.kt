package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.MavericksViewModelFactory
import com.rixspi.common.domain.interactors.CreateNote
import com.rixspi.common.framework.di.AssistedViewModelFactory
import com.rixspi.common.framework.di.hiltMavericksViewModelFactory
import com.rixspi.common.presentation.BaseViewModel
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

    fun command(show: Boolean){
        setState { copy(command = show) }
    }

    fun addNote(parentNote: EditableNoteItem2) {
        // Random uuid is totally fine for now, but if used anywhere in `setState` block, then
        // the reducer will be impure, because if run twice we will get two different UUIDs
        // I don't want to turn off debug validation from Maverick, so this is the simplest solution
        val id = UUID.randomUUID().toString()
        setState {
            addChildrenNote(parentNote = parentNote, id = id)
        }
    }

    fun addNoteTemp() {
        // Random uuid is totally fine for now, but if used anywhere in `setState` block, then
        // the reducer will be impure, because if run twice we will get two different UUIDs
        // I don't want to turn off debug validation from Maverick, so this is the simplest solution
        val id = UUID.randomUUID().toString()
        setState {
            addChildrenNote(id = id)
        }
    }

    fun removeNote(noteId: String) {
        setState {
            removeChildrenNote(noteId)
        }
    }

    fun updateTitle(noteId: String, title: String) {
        setState {
            setTitle(noteId, title = title)
        }
    }

    fun addContent(note: EditableNoteItem, index: Int) {
        val id = UUID.randomUUID().toString()
        //setState { addContentInfo(note, id, index) }
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
