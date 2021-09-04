package com.rixspi.common.domain.interactors

import com.rixspi.common.domain.model.Note
import com.rixspi.common.domain.repository.NoteRepository
import com.rixspi.domain.Result
import com.rixspi.domain.interactor.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CreateNote(
    private val noteReader: NoteRepository,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : SuspendUseCase<CreateNote.Params, String>(
    coroutineDispatcher
) {
    data class Params(val notes: List<Note>)

    override suspend fun run(parameters: Params): Result<String> {
        val descendants = parameters.notes.subList(1, parameters.notes.size)
        val note = parameters.notes.first().copy(childrenNotes = descendants)
        return noteReader.createNote(note)
    }
}
