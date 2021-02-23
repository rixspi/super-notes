package com.rixspi.notes.domain.interactors

import com.rixspi.domain.Result
import com.rixspi.domain.interactor.FlowParameterLessUseCase

import com.rixspi.domain.model.Note
import com.rixspi.common.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

// TODO Watch this default dispatcher https://www.techyourchance.com/coroutines-dispatchers-default-and-dispatchers-io-considered-harmful/
class GetNotes(
    private val noteReader: NoteRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : FlowParameterLessUseCase<List<Note>>(coroutineDispatcher) {

    override fun run(): Flow<Result<List<Note>>> {
        return noteReader.getNotes()
    }
}