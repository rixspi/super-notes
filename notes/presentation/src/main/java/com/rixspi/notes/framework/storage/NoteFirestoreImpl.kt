package com.rixspi.notes.framework.storage

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestoreException
import com.rixspi.common.domain.model.Note
import com.rixspi.data.dataSource.NoteFirestore
import com.rixspi.data.model.NoteDto
import com.rixspi.domain.Result
import com.rixspi.domain.toError
import com.rixspi.domain.util.empty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalCoroutinesApi::class)
class NoteFirestoreImpl(
    private val notes: CollectionReference
) : NoteFirestore {

    override fun getNotes(): Flow<Result<List<NoteDto>>> = callbackFlow {
        val subscription = notes.addSnapshotListener { value, e ->
            e?.let {
                offer(Result.Failure(it.toError()))
                Log.e("Firebase error", it.localizedMessage ?: String.empty)
                return@addSnapshotListener
            }
            if (value?.isEmpty == false) {
                val notes = value.documents.mapNotNull {
                    it.toObject(NoteDto::class.java)?.copy(id = it.id)
                }
                offer(Result.Success(notes))
            } else {
                offer(Result.Success(emptyList<NoteDto>()))
            }
        }
        awaitClose { subscription.remove() }
    }

    override suspend fun createNote(note: Note): Result<String> {
        return try {
            val sth = notes.add(note).await()
            Result.Success(sth.id)
        } catch (e: FirebaseFirestoreException) {
            Result.Failure(e.toError())
        }
    }

    override suspend fun deleteNote(noteId: String): Result<String> {
        return try {
            val sth = notes.document(noteId).delete()
            Result.Success(noteId)
        } catch (e: FirebaseFirestoreException) {
            Result.Failure(e.toError())
        }
    }
}
