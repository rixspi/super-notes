package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.*
import com.rixspi.common.domain.model.Note
import com.rixspi.common.framework.di.AssistedViewModelFactory
import com.rixspi.common.framework.di.hiltMavericksViewModelFactory
import com.rixspi.common.domain.interactors.CreateNote
import com.rixspi.common.domain.model.ContentInfo
import com.rixspi.common.presentation.BaseViewModel
import com.rixspi.notes.presentation.mapper.toNote
import com.rixspi.notes.presentation.model.EditableContentInfoItem
import com.rixspi.notes.presentation.model.EditableNoteItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AddNoteViewModel @AssistedInject constructor(
    @Assisted state: AddNoteViewState,
    private val createNote: CreateNote
) : BaseViewModel<AddNoteViewState>(state) {

    fun addNote() {
        setState {
            addChildrenNote()
        }
    }

    fun removeNote() {
        // TODO
    }

    fun updateTitle(title: String) {
        setState {
            setTitle(title = title)
        }
    }

    fun addContent(note: EditableNoteItem, index: Int) = setState { addContentInfo(note, index) }

    fun updateContentInfo(note: EditableNoteItem, index: Int, text: String) =
        setState { updateContentInfo(note = note, index = index, text = text) }

    fun removeContentInfo(note: EditableNoteItem, index: Int) = setState { removeContentInfo() }

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
