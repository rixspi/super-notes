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
import junit.framework.Assert.assertEquals
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

@ExperimentalTime
class NoteRepositoryImplTest : NoteRepositoryTest() {

    private val noteFirestore: NoteFirestore = object : NoteFirestore {
        private val notesList = mutableListOf<NoteDto>()
        private val notesFlow = MutableStateFlow(notesList.toList())

        override fun getNotes(): Flow<Result<List<NoteDto>>> =
            notesFlow.asStateFlow().map {
                Result.Success(it)
            }

        override suspend fun createNote(note: Note): Result<String> {
            notesList.add(NoteDto(id = note.id))
            notesFlow.value = notesList.toList()

            return Result.Success(NoteDto().id)
        }

        override suspend fun deleteNote(noteId: String): Result<String> {
            val removed = notesList.remove(notesList.firstOrNull { it.id == noteId })
            notesFlow.value = notesList.toList()
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
