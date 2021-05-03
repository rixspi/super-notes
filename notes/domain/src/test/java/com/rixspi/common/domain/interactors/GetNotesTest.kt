package com.rixspi.common.domain.interactors

import app.cash.turbine.test
import com.rixspi.common.domain.model.Note
import com.rixspi.common.domain.repository.NoteRepository
import com.rixspi.domain.Result
import com.rixspi.domain.toError
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@ExperimentalTime
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

    @Test
    fun `when there is only one note return a list with one `() {
        runBlocking {
            val note = Note(id = "note")
            val success = Result.Success(listOf(note))
            coEvery { noteRepository.getNotes() } returns flowOf(success)

            getNotes().test {
                assertEquals(success, expectItem())
                expectComplete()
            }
        }
    }

    @Test
    fun `when getting is unsuccessful return meaningful error`() {
        runBlocking {
            val failure = Result.Failure(IllegalStateException().toError())
            coEvery { noteRepository.getNotes() } returns flowOf(failure)

            getNotes().test {
                assertEquals(failure, expectItem())
                expectComplete()
            }
        }
    }

    @Test
    fun `when getting is unsuccessful then successful return meaningful error`() {
        runBlocking {
            val note = Note(id = "note")
            val failure = Result.Failure(IllegalStateException().toError())
            val success = Result.Success(listOf(note))
            coEvery { noteRepository.getNotes() } returns flowOf(failure, success)

            getNotes().test {
                assertEquals(failure, expectItem())
                assertEquals(success, expectItem())
                expectComplete()
            }
        }
    }
}
