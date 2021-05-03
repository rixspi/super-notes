package com.rixspi.notes.framework.di

import com.google.firebase.firestore.FirebaseFirestore
import com.rixspi.common.domain.interactors.CreateNote
import com.rixspi.common.domain.interactors.DeleteNote
import com.rixspi.common.domain.interactors.GetNotes
import com.rixspi.common.domain.model.Note
import com.rixspi.common.domain.repository.NoteRepository
import com.rixspi.data.dataSource.NoteFirestore
import com.rixspi.data.mapper.mapContentInfoDto
import com.rixspi.data.mapper.mapList
import com.rixspi.data.mapper.mapNoteDto
import com.rixspi.data.model.NoteDto
import com.rixspi.data.notes.NoteRepositoryImpl
import com.rixspi.notes.framework.storage.NoteFirestoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(SingletonComponent::class)
object NotesModule {

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideFirestoreNoteStorage(firestore: FirebaseFirestore): NoteFirestore {
        // TODO Create constant for this collection name
        return NoteFirestoreImpl(firestore.collection("notes"))
    }

    @Provides
    fun provideNotesListDtoMapper(): (List<NoteDto>) -> List<Note> = { dtoList ->
        mapList(dtoList) { listDto ->
            mapNoteDto(listDto) { mapList(it) { dto -> mapContentInfoDto(dto) } }
        }
    }

    /**
     * [@JvmSupressWildcards] is needed for lambda return type used by noteDtoMapper to work in kotlin
     */
    @JvmSuppressWildcards
    @Provides
    fun provideNoteReader(
        noteFirestore: NoteFirestore,
        noteDtoMapper: (List<NoteDto>) -> List<Note>
    ): NoteRepository =
        NoteRepositoryImpl(noteFirestore, noteDtoMapper)

    @Provides
    fun provideGetNotes(noteReader: NoteRepository): GetNotes = GetNotes(noteReader)

    @Provides
    fun provideCreateNote(noteReader: NoteRepository): CreateNote = CreateNote(noteReader)

    @Provides
    fun provideDeleteNote(noteReader: NoteRepository): DeleteNote = DeleteNote(noteReader)
}
