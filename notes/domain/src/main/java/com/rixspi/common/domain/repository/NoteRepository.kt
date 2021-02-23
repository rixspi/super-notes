package com.rixspi.common.domain.repository

import com.rixspi.domain.Result
import com.rixspi.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<Result<List<Note>>>

    suspend fun createNote(note: Note): Result<String>
}