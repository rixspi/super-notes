package com.rixspi.supernotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rixspi.supernotes.databinding.ActivityMainBinding
import com.rixspi.supernotes.domain.model.ContentInfoEntity
import com.rixspi.supernotes.domain.model.NoteEntity
import com.rixspi.supernotes.serialization.MapInput
import com.rixspi.supernotes.serialization.MapOutput
import kotlinx.serialization.InternalSerializationApi

private const val NOTES_COLLECTION = "notes"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @InternalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val asdf = NoteEntity("note4")
        val note = NoteEntity(
            "note3",
            childrenNotes = listOf(asdf)
        )

        val notes = mutableListOf<NoteEntity>()
        val contentInfos = mutableListOf<ContentInfoEntity>()

        val db = Firebase.firestore
        db.collection(NOTES_COLLECTION).get()
            .addOnSuccessListener {query ->
                query.documents.forEach { contentInfo ->
                    contentInfo.toObject(ContentInfoEntity::class.java)
                        ?.copy(id = contentInfo.id)
                        ?.also { contentInfos.add(it) }
                }
            }

        binding.textView.setOnClickListener {
            db.collection(NOTES_COLLECTION).add(
                
            )
        }
    }
}
