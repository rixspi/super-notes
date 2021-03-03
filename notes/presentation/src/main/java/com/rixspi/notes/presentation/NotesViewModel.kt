package com.rixspi.notes.presentation

import com.airbnb.mvrx.*
import com.rixspi.common.domain.model.Note
import com.rixspi.common.framework.di.AssistedViewModelFactory
import com.rixspi.common.framework.di.hiltMavericksViewModelFactory
import com.rixspi.common.domain.interactors.GetNotes
import com.rixspi.common.presentation.BaseViewModel
import com.rixspi.data.mapper.mapList
import com.rixspi.domain.fold
import com.rixspi.notes.presentation.mapper.mapContentInfo
import com.rixspi.notes.presentation.mapper.mapNote
import com.rixspi.notes.presentation.mapper.mapNotesList
import com.rixspi.notes.presentation.model.NoteListItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.lang.IllegalStateException

data class NotesViewState(
    val notes: Async<List<NoteListItem>> = Uninitialized
) : MavericksState

class NotesViewModel @AssistedInject constructor(
    @Assisted state: NotesViewState,
    getNotes: GetNotes,
) : BaseViewModel<NotesViewState>(state) {


    init {
        getNotes().execute(
            mapper = { notes -> mapNotesList(notes) }
        ) {
            copy(notes = it)
        }
    }

    fun removeNote(note: NoteListItem) = setState {
        val notes =
            this.notes()?.toMutableList()?.apply { remove(note) }?.toList() ?: emptyList()
        copy(notes = Success(notes))
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<NotesViewModel, NotesViewState> {
        override fun create(state: NotesViewState): NotesViewModel
    }

    companion object :
        MavericksViewModelFactory<NotesViewModel, NotesViewState> by hiltMavericksViewModelFactory()
}