package com.rixspi.data.dataSource

import com.rixspi.common.domain.model.Note
import com.rixspi.data.model.NoteDto
import com.rixspi.domain.Result
import kotlinx.coroutines.flow.Flow

/**
 * Implemented in the framework layer in order to provide firebase dependencies
 */
interface NoteFirestore {
    fun getNotes(): Flow<Result<List<NoteDto>>>

    suspend fun createNote(note: Note): Result<String>

    suspend fun deleteNote(noteId: String): Result<String>
}
