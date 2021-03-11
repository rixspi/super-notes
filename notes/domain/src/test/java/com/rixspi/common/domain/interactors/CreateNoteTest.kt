package com.rixspi.common.domain.interactors

import com.rixspi.common.domain.model.Note
import com.rixspi.common.domain.repository.NoteRepository
import com.rixspi.domain.Error
import com.rixspi.domain.Result
import com.rixspi.domain.toError
import com.rixspi.domain.util.empty
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException


class CreateNoteTest {
    private lateinit var createNote: CreateNote
    private val noteRepository: NoteRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        createNote = CreateNote(
            noteReader = noteRepository,
            coroutineDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun `when creation is successful return note id as a string`() {
        runBlocking {
            val note = Note()
            val success = Result.Success(note.id)
            coEvery { noteRepository.createNote(note) } returns success

            val result = createNote(CreateNote.Params(note = note))

            assertEquals(success, result)
        }
    }

    @Test
    fun `when creation is unsuccessful return meaningful error`() {
        runBlocking {
            val exception = IllegalStateException()
            val note = Note()

            coEvery { noteRepository.createNote(note) } throws exception

            val result = createNote(CreateNote.Params(note = note))

            assertEquals(Result.Failure(exception.toError()), result)
        }
    }
}