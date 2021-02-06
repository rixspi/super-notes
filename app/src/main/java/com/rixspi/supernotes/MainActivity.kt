package com.rixspi.supernotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.rixspi.notes.presentation.MyComposable
import com.rixspi.supernotes.ui.styling.SuperNoteTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            SuperNoteTheme {
                MyComposable()
            }
        }
    }
}
