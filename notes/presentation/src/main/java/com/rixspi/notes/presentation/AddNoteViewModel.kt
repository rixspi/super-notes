package com.rixspi.notes.presentation

import com.airbnb.mvrx.*
import com.rixspi.common.domain.model.Note
import com.rixspi.common.framework.di.AssistedViewModelFactory
import com.rixspi.common.framework.di.hiltMavericksViewModelFactory
import com.rixspi.common.domain.interactors.CreateNote
import com.rixspi.common.presentation.BaseViewModel
import com.rixspi.domain.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class AddNoteViewModel @AssistedInject constructor(
    @Assisted state: AddNoteViewState,
    private val createNote: CreateNote
) : BaseViewModel<AddNoteViewState>(state) {

    fun updateTitle(title: String) {
        setState {
            setTitle(title = title)
        }
    }

    fun addContent() = setState { addContentInfo() }

    fun updateContentInfo(index: Int, text: String) =
        setState { updateContentInfo(index = index, text = text) }

    fun removeContentInfo() = setState { removeContentInfo() }

    fun removeNote(note: Note) {
//        setState {
//            copy(notes = notes - note)
//        }
    }

    fun createNote() = withState { state ->
        createNote.execute(
            params = CreateNote.Params(state.note),
            mapper = {}
        ) { this }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<AddNoteViewModel, AddNoteViewState> {
        override fun create(state: AddNoteViewState): AddNoteViewModel
    }

    companion object :
        MavericksViewModelFactory<AddNoteViewModel, AddNoteViewState> by hiltMavericksViewModelFactory()
}


