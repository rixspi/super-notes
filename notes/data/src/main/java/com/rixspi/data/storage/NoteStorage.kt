package com.rixspi.data.storage


import com.rixspi.domain.Result
import com.rixspi.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteStorage {
    fun getNotes(): Flow<Result<List<Note>>>

    suspend fun createNote(note: Note): Result<String>
}