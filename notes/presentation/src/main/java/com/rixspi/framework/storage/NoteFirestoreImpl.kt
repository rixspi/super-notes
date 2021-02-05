package com.rixspi.framework.storage

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestoreException
import com.rixspi.data.dataSource.NoteFirestore
import com.rixspi.data.model.NoteDto
import com.rixspi.domain.Error
import com.rixspi.domain.Result
import com.rixspi.domain.model.Note

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class NoteFirestoreImpl(
    private val notes: CollectionReference
) : NoteFirestore {
    override fun getNotes(): Flow<Result<List<NoteDto>>> = callbackFlow {

        val subscription = notes.addSnapshotListener { value, e ->
            e?.let {
                offer(Result.Failure(Error.UnspecifiedError))
                Log.e("Firebase error", it.localizedMessage ?: "")
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
            Result.Failure(Error.UnspecifiedError)
        }
    }
}