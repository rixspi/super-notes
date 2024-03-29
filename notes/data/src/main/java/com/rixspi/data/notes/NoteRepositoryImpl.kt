package com.rixspi.data.notes

import com.rixspi.common.domain.model.Note
import com.rixspi.common.domain.repository.NoteRepository
import com.rixspi.data.dataSource.NoteFirestore
import com.rixspi.data.model.NoteDto
import com.rixspi.domain.Result
import com.rixspi.domain.fold
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteFirestore: NoteFirestore,
    private val noteDtoMapper: (List<NoteDto>) -> List<Note>
) : NoteRepository {
    override fun getNotes(): Flow<Result<List<Note>>> =
        noteFirestore.getNotes().map {
            it.fold(
                success = { dto -> Result.Success(noteDtoMapper(dto)) },
                error = { throwable -> Result.Failure(throwable) }
            )
        }

    override suspend fun createNote(note: Note): Result<String> = noteFirestore.createNote(note)

    override suspend fun deleteNote(noteId: String): Result<String> =
        noteFirestore.deleteNote(noteId)
}
