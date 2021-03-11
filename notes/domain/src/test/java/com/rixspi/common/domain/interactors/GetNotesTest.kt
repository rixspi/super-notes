package com.rixspi.common.domain.interactors

import com.rixspi.common.domain.model.Note
import com.rixspi.common.domain.repository.NoteRepository
import com.rixspi.domain.Result
import com.rixspi.domain.toError
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException


class GetNotesTest {
    private lateinit var getNotes: GetNotes
    private val noteRepository: NoteRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getNotes = GetNotes(
            noteReader = noteRepository,
            coroutineDispatcher = Dispatchers.Unconfined
        )
    }

    // TODO Find a clean way of testing flows

    @Test
    fun `when deletion is successful return note id`() {
        runBlocking {
            val noteId = "note"
            val success = Result.Success(emptyList<Note>())
            coEvery { noteRepository.getNotes() } returns flow {  }


        }
    }


    @Test
    fun `when deletion is unsuccessful return meaningful error`() {
        runBlocking {
            val noteId = "note"
            val exception = IllegalStateException()



        }
    }
}