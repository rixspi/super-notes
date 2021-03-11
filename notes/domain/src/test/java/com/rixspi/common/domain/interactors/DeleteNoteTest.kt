package com.rixspi.common.domain.interactors

import com.rixspi.common.domain.repository.NoteRepository
import com.rixspi.domain.Result
import com.rixspi.domain.toError
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException

class DeleteNoteTest {
    private lateinit var deleteNote: DeleteNote
    private val noteRepository: NoteRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        deleteNote = DeleteNote(
            noteReader = noteRepository,
            coroutineDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun `when deletion is successful return note id`() {
        runBlocking {
            val noteId = "note"
            val success = Result.Success(noteId)
            coEvery { noteRepository.deleteNote(noteId) } returns success

            val result = deleteNote(DeleteNote.Params(noteId))

            assertEquals(success, result)
        }
    }


    @Test
    fun `when deletion is unsuccessful return meaningful error`() {
        runBlocking {
            val noteId = "note"
            val exception = IllegalStateException()

            coEvery { noteRepository.deleteNote(noteId) } throws exception

            val result = deleteNote(DeleteNote.Params(noteId))

            assertEquals(Result.Failure(exception.toError()), result)
        }
    }
}