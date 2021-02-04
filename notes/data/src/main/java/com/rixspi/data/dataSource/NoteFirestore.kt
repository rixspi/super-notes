package com.rixspi.data.dataSource


import com.rixspi.data.model.NoteDto
import com.rixspi.domain.Result
import com.rixspi.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Implemented in the framework layer in order to provide firebase dependencies
 */
interface NoteFirestore {
    fun getNotes(): Flow<Result<List<NoteDto>>>

    suspend fun createNote(note: Note): Result<String>
}