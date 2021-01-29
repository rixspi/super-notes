package com.rixspi.data.notes

import com.rixspi.data.storage.NoteStorage
import com.rixspi.domain.Result
import com.rixspi.domain.model.Note
import com.rixspi.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow


class NoteRepositoryImpl(
    private val noteStorage: NoteStorage
) : NoteRepository {
    override fun getNotes(): Flow<Result<List<Note>>> = noteStorage.getNotes()
    override suspend fun createNote(note: Note): Result<String> = noteStorage.createNote(note)
}