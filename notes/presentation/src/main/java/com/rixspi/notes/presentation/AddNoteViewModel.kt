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

data class AddNoteViewState(
    val note: Note = Note(contentInfos = listOf(com.rixspi.domain.model.ContentInfo()))
) : MavericksState

class AddNoteViewModel @AssistedInject constructor(
    @Assisted state: AddNoteViewState,
    private val createNote: CreateNote
) : MavericksViewModel<AddNoteViewState>(state) {

    fun updateTitle(title: String) {
        setState {
            copy(note = note.copy(title = title))
        }
    }

    fun addContent() {
        setState {
            val contentInfos =
                note.contentInfos + com.rixspi.domain.model.ContentInfo()
            copy(note = note.copy(contentInfos = contentInfos))
        }
    }

    fun updateContentInfo(index: Int, text: String) {
        setState {
            val contentInfo = note.contentInfos[index].copy(text = text)
            val contentInfos = note
                .contentInfos
                .toMutableList()
                .apply {
                    set(index = index, contentInfo)
                }
            val note = note.copy(contentInfos = contentInfos)
            copy(note = note)
        }
    }

    fun removeContentInfo() {
        setState {
            val contentInfo = note
                .contentInfos
                .toMutableList()
                .apply {
                    removeLast()
                }
            val note = note.copy(contentInfos = contentInfo)
            copy(note = note)
        }
    }

    fun removeNote(note: Note) {
//        setState {
//            copy(notes = notes - note)
//        }
    }

    fun createNote() = withState { state ->
        viewModelScope.launch {
            createNote(
                CreateNote.Params(
                    state.note
                )
            )
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<AddNoteViewModel, AddNoteViewState> {
        override fun create(state: AddNoteViewState): AddNoteViewModel
    }

    companion object :
        MavericksViewModelFactory<AddNoteViewModel, AddNoteViewState> by hiltMavericksViewModelFactory()
}