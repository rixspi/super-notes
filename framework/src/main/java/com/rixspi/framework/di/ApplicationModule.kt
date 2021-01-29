package com.rixspi.framework.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rixspi.data.notes.NoteRepositoryImpl
import com.rixspi.data.storage.NoteStorage
import com.rixspi.framework.storage.FirestoreNoteStorage
import com.rixspi.notes.domain.interactors.CreateNote
import com.rixspi.notes.domain.interactors.GetNotes
import com.rixspi.notes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

// As a dependency of another class.
@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideFirestoreNoteStorage(firestore: FirebaseFirestore): NoteStorage {
        return FirestoreNoteStorage(firestore.collection("notes"))
    }

    @Provides
    fun provideNoteReader(noteStorage: NoteStorage): NoteRepository =
        NoteRepositoryImpl(noteStorage)

    @Provides
    fun provideGetNotes(noteReader: NoteRepository): GetNotes = GetNotes(noteReader)

    @Provides
    fun provideCreateNote(noteReader: NoteRepository): CreateNote = CreateNote(noteReader)
}
