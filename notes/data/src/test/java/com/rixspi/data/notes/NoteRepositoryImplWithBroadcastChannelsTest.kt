package com.rixspi.data.notes

import com.rixspi.common.domain.model.Note
import com.rixspi.common.domain.repository.NoteRepository
import com.rixspi.common.domain.repository.NoteRepositoryTest
import com.rixspi.data.dataSource.NoteFirestore
import com.rixspi.data.mapper.mapContentInfoDto
import com.rixspi.data.mapper.mapList
import com.rixspi.data.mapper.mapNoteDto
import com.rixspi.data.model.NoteDto
import com.rixspi.domain.Error
import com.rixspi.domain.Result
import junit.framework.Assert
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import kotlin.random.Random
import kotlin.time.ExperimentalTime


@ExperimentalTime
class NoteRepositoryImplWithBroadcastChannelsTest : NoteRepositoryTest() {

    private val noteFirestore: NoteFirestore = object : NoteFirestore {
        private val notesList = mutableListOf<NoteDto>()
        private val notesChannel = ConflatedBroadcastChannel<List<NoteDto>>()

        init {
            runBlocking {
                // In opposite to StateFlow, channel doesn't have the initial value,
                //  this is a broadcast channel so every active receiver will get the event
                notesChannel.send(notesList)
            }
        }

        override fun getNotes(): Flow<Result<List<NoteDto>>> =
            notesChannel.asFlow().map { Result.Success(it) }

        override suspend fun createNote(note: Note): Result<String> {
            notesList.add(NoteDto(id = note.id))
            notesChannel.send(notesList.toList())

            return Result.Success(NoteDto().id)
        }

        override suspend fun deleteNote(noteId: String): Result<String> {
            val removed = notesList.remove(notesList.firstOrNull { it.id == noteId })
            notesChannel.send(notesList.toList())
            return if (removed) {
                Result.Success(noteId)
            } else {
                Result.Failure(Error.ElementNotFound())
            }
        }
    }

    private val mapper: (List<NoteDto>) -> List<Note> = { dtoList ->
        mapList(dtoList) { listDto ->
            mapNoteDto(listDto) { mapList(it) { dto -> mapContentInfoDto(dto) } }
        }
    }

    override val noteRepository: NoteRepository = NoteRepositoryImpl(
        noteFirestore,
        mapper
    )

    override fun assertionForTwoConsumers(expected: Any?, actual: Any?) {
        assertEquals(expected, actual)
    }
}
