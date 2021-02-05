package com.rixspi.presentation

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.rixspi.domain.Result
import com.rixspi.domain.model.Note
import com.rixspi.framework.di.AssistedViewModelFactory
import com.rixspi.framework.di.hiltMavericksViewModelFactory
import com.rixspi.notes.domain.interactors.CreateNote
import com.rixspi.notes.domain.interactors.GetNotes
import com.rixspi.notes.domain.repository.NoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.onEach

data class NotesViewState(
    val notes: Async<Result<List<Note>>>
) : MavericksState

class NotesViewModel @AssistedInject constructor(
    @Assisted state: NotesViewState,
    private val getNotes: GetNotes,
    private val createNote: CreateNote
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