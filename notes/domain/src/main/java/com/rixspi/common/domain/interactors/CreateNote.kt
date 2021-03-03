package com.rixspi.common.domain.interactors

import com.rixspi.domain.Result
import com.rixspi.domain.interactor.SuspendUseCase
import com.rixspi.common.domain.model.Note
import com.rixspi.common.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CreateNote(
    private val noteReader: NoteRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : SuspendUseCase<CreateNote.Params, String>(
    coroutineDispatcher
) {
    data class Params(val note: Note)

    override suspend fun run(parameters: Params): Result<String> {
        return noteReader.createNote(parameters.note)
    }
}