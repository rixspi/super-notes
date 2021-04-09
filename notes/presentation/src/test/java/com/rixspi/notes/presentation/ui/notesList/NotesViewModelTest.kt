package com.rixspi.notes.presentation.ui.notesList

import com.airbnb.mvrx.*
import com.rixspi.common.domain.interactors.DeleteNote
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


class NotesViewModelTest{}