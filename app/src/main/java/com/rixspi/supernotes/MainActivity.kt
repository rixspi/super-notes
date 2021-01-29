package com.rixspi.supernotes

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.collection.LLRBNode
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rixspi.domain.doOnError
import com.rixspi.domain.doOnSuccess
import com.rixspi.domain.model.Note
import com.rixspi.notes.domain.interactors.CreateNote
import com.rixspi.notes.domain.interactors.GetNotes
import com.rixspi.supernotes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var getNotes: GetNotes
    @Inject lateinit var createNotes: CreateNote

    @InternalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getNotes().onEach {
            it.doOnSuccess {
                Log.e("Notes", it.toString())
                binding.textView.text = it.toString()
            }
            it.doOnError {
                Log.e("Notes", "Error :( $it")
            }
        }.launchIn(MainScope())


        binding.button.setOnClickListener{
            MainScope().launch {
                createNotes(
                    CreateNote.Params(
                        Note(id = "user-friendly-id-1")
                    )
                )
            }
        }
    }
}
