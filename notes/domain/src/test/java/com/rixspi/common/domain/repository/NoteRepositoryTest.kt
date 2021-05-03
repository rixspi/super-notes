package com.rixspi.common.domain.repository

import app.cash.turbine.test
import com.rixspi.common.domain.model.Note
import com.rixspi.domain.Error
import com.rixspi.domain.Result
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalTime
abstract class NoteRepositoryTest {

    abstract val noteRepository: NoteRepository

    @Test
    fun `when note created it is returned by getNotes`() {
        runBlocking {
            val note = Note(id = "note")
            noteRepository.createNote(note)
            noteRepository.getNotes().test {
                assertEquals(Result.Success(listOf(note)), expectItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when note removed it is not returned by getNotes`() {
        runBlocking {
            val note = Note(id = "note")
            noteRepository.createNote(note)
            noteRepository.deleteNote(note.id)
            noteRepository.getNotes().test {
                assertEquals(Result.Success(emptyList<Note>()), expectItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when there are no notes return an error on remove`() {
        runBlocking {
            val note = Note(id = "note")
            val result = noteRepository.deleteNote(note.id)
            assertEquals(Result.Failure(Error.ElementNotFound()), result)
        }
    }

    abstract fun assertionForTwoConsumers(expected: Any?, actual: Any?)

    @Test
    fun simulateTwoConsumers() = runBlocking {
        val notesListsCollected = mutableListOf<List<Note>>()
        val notesListsCollected2 = mutableListOf<List<Note>>()

        val firstConsumer = noteRepository.getNotes().onEach {
            notesListsCollected.add(it.invoke()!!)
            println("FIRST  : ${it.invoke()}")
        }.launchIn(this)

        val secondConsumer = noteRepository.getNotes().onEach {
            notesListsCollected2.add(it.invoke()!!)
            println("SECOND : ${it.invoke()}")
        }.launchIn(this)

        // Delays here are necessary for having separate `getNotes` triggers as without it the conflated
        //  StateFlow will "flatten" two or more calls, as we have only one value in there
        val notesCount = 10
        repeat(notesCount) {
            delay(5)
            noteRepository.createNote(Note(it.toString()))
        }

        assertionForTwoConsumers(notesCount, notesListsCollected.size)
        assertionForTwoConsumers(notesCount, notesListsCollected2.size)

        firstConsumer.cancel()
        secondConsumer.cancel()
    }

    @Test
    fun `flow testing with runBlockingTest`() {
        runBlockingTest {
            val notesListsCollected = mutableListOf<List<Note>>()

            val job = noteRepository.getNotes().onEach {
                notesListsCollected.add(it.invoke()!!)
                println("Notes list: ")
                it.invoke()?.forEach {
                    println(it)
                }
            }.launchIn(this)

            noteRepository.createNote(Note("1"))
            noteRepository.createNote(Note("2"))
            noteRepository.createNote(Note("3"))

            notesListsCollected.forEachIndexed { index, list ->
                assertEquals(index, list.size)
            }

            job.cancel()
        }
    }
}
