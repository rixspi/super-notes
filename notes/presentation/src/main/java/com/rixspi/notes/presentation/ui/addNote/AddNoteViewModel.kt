package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.*
import com.rixspi.common.framework.di.AssistedViewModelFactory
import com.rixspi.common.framework.di.hiltMavericksViewModelFactory
import com.rixspi.common.domain.interactors.CreateNote
import com.rixspi.common.presentation.BaseViewModel
import com.rixspi.notes.presentation.mapper.toNote
import com.rixspi.notes.presentation.model.EditableNoteItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.*

class AddNoteViewModel @AssistedInject constructor(
    @Assisted state: AddNoteViewState,
    private val createNote: CreateNote
) : BaseViewModel<AddNoteViewState>(state) {

    fun addNote(parentNote: EditableNoteItem, index: Int = 0) {
        // Random uuid is totally fine for now, but if used anywhere in `setState` block, then
        // the reducer will be impure, because if run twice we will get two different UUIDs
        // I don't want to turn off debug validation from Maverick, so this is the simplest solution
        val id = UUID.randomUUID().toString()
        setState {
            addChildrenNote(note = parentNote, id = id, index = index)
        }
    }

    fun addNote(index: Int = 0) {
        val id = UUID.randomUUID().toString()
        setState {
            addChildrenNote(id = id, index = index)
        }
    }

    fun removeNote(parentNote: EditableNoteItem, index: Int) {
        setState {
            removeChildrenNote(parentNote, index)
        }
    }

    fun removeNote(index: Int) {
        setState {
            removeChildrenNote(index = index)
        }
    }

    fun updateTitle(parentNote: EditableNoteItem, title: String) {
        setState {
            setTitle(parentNote, title = title)
        }
    }

    fun updateTitle(title: String) {
        setState {
            setTitle(title = title)
        }
    }

    fun addContent(note: EditableNoteItem, index: Int) {
        val id = UUID.randomUUID().toString()
        setState { addContentInfo(note, id, index) }
    }

    fun addContent(index: Int) {
        val id = UUID.randomUUID().toString()
        setState { addContentInfo(id = id, index = index) }
    }

    fun updateContentInfo(note: EditableNoteItem, index: Int, text: String) =
        setState { updateContentInfo(note = note, index = index, text = text) }

    fun updateContentInfo(index: Int, text: String) =
        setState { updateContentInfo(index = index, text = text) }

    fun removeContentInfo(note: EditableNoteItem, index: Int) =
        setState { removeContentInfo(note, index) }

    fun removeContentInfo(index: Int) = setState { removeContentInfo(index = index) }

    fun createNote() = withState { state ->
        createNote.execute(
            params = CreateNote.Params(state.note.toNote())
        ) { copy(added = true) }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<AddNoteViewModel, AddNoteViewState> {
        override fun create(state: AddNoteViewState): AddNoteViewModel
    }

    companion object :
        MavericksViewModelFactory<AddNoteViewModel, AddNoteViewState> by hiltMavericksViewModelFactory()
}
