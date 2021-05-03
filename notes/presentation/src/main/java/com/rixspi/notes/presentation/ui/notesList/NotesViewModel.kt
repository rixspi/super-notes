package com.rixspi.notes.presentation

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.rixspi.common.domain.interactors.DeleteNote
import com.rixspi.common.domain.interactors.GetNotes
import com.rixspi.common.framework.di.AssistedViewModelFactory
import com.rixspi.common.framework.di.hiltMavericksViewModelFactory
import com.rixspi.common.presentation.BaseViewModel
import com.rixspi.notes.presentation.mapper.mapNotesList
import com.rixspi.notes.presentation.model.NoteListItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

data class NotesViewState(
    val notes: Async<List<NoteListItem>> = Uninitialized
) : MavericksState

class NotesViewModel @AssistedInject constructor(
    @Assisted state: NotesViewState,
    getNotes: GetNotes,
    private val deleteNote: DeleteNote
) : BaseViewModel<NotesViewState>(state) {

    init {
        getNotes().execute(
            mapper = { notes -> mapNotesList(notes) }
        ) {
            copy(notes = it)
        }
    }

    fun removeNote(note: NoteListItem) = withState {
        deleteNote.execute(
            params = DeleteNote.Params(note.id),
            mapper = {},
        ) { this }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<NotesViewModel, NotesViewState> {
        override fun create(state: NotesViewState): NotesViewModel
    }

    companion object :
        MavericksViewModelFactory<NotesViewModel, NotesViewState> by hiltMavericksViewModelFactory()
}
