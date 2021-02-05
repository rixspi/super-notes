package com.rixspi.supernotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.rixspi.supernotes.databinding.ActivityMainBinding
import com.rixspi.supernotes.ui.styling.SuperNoteTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.InternalSerializationApi


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @InternalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            SuperNoteTheme() {

            }
        }
    }
}
