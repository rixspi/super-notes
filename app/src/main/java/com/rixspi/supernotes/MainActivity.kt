package com.rixspi.supernotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rixspi.supernotes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.InternalSerializationApi


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    @InternalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        getNotes().onEach {
//            it.doOnSuccess {
//                Log.e("Notes", it.toString())
//                binding.textView.text = it.toString()
//            }
//            it.doOnError {
//                Log.e("Notes", "Error :( $it")
//            }
//        }.launchIn(MainScope())
//
//
//        binding.button.setOnClickListener{
//            MainScope().launch {
//                createNotes(
//                    CreateNote.Params(
//                        Note(id = "user-friendly-id-1")
//                    )
//                )
//            }
//        }
    }
}
