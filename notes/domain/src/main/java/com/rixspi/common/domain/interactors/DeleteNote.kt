package com.rixspi.common.domain.interactors

import com.rixspi.domain.Result
import com.rixspi.domain.interactor.SuspendUseCase
import com.rixspi.common.domain.model.Note
import com.rixspi.common.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteNote(
    private val noteReader: NoteRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : SuspendUseCase<DeleteNote.Params, String>(
    coroutineDispatcher
) {
    data class Params(val noteId: String)

    override suspend fun run(parameters: Params): Result<String> {
        return noteReader.deleteNote(parameters.noteId)
    }
}