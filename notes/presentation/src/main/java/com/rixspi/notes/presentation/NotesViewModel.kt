package com.rixspi.notes.presentation

import com.airbnb.mvrx.*
import com.rixspi.domain.Result
import com.rixspi.domain.model.Note
import com.rixspi.common.framework.di.AssistedViewModelFactory
import com.rixspi.common.framework.di.hiltMavericksViewModelFactory
import com.rixspi.common.domain.interactors.CreateNote
import com.rixspi.notes.domain.interactors.GetNotes
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

data class NotesViewState(
    val notes: Async<Result<List<Note>>> = Uninitialized
) : MavericksState

class NotesViewModel @AssistedInject constructor(
    @Assisted state: NotesViewState,
    getNotes: GetNotes,
) : MavericksViewModel<NotesViewState>(state) {

    init {
        getNotes().execute { copy(notes = it) }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<NotesViewModel, NotesViewState> {
        override fun create(state: NotesViewState): NotesViewModel
    }

    companion object :
        MavericksViewModelFactory<NotesViewModel, NotesViewState> by hiltMavericksViewModelFactory()
}