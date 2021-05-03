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
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals

@ExperimentalTime
class NoteRepositoryImplWithChannelsTest : NoteRepositoryTest() {

    private val noteFirestore: NoteFirestore = object : NoteFirestore {
        private val notesList = mutableListOf<NoteDto>()
        private val notesChannel = Channel<List<NoteDto>>(CONFLATED)

        init {
            runBlocking {
                // In opposite to StateFlow, channel doesn't have the initial value,
                //  also as it uses normal channel receiver "consumes" the event, so if
                //  there are two receivers only one will get the event
                notesChannel.send(notesList)
            }
        }

        override fun getNotes(): Flow<Result<List<NoteDto>>> =
            notesChannel.consumeAsFlow().map { Result.Success(it) }

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

    /**
     * Assuming that there are two consumers for the 'notesChannel' in the above impl of the
     * 'NoteFireStore' when using this kind of channel (Conflated), when consumer will consume the event,
     * it's no longer in that channel, so the second consumer will not receive it
     */
    override fun assertionForTwoConsumers(expected: Any?, actual: Any?) {
        assertNotEquals(expected, actual)
    }
}
