package com.rixspi.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.airbnb.mvrx.InternalMavericksApi
import com.rixspi.presentation.mvrx.collectState
import com.rixspi.presentation.mvrx.mavericksViewModel

@InternalMavericksApi
@Composable
fun MyComposable() {
    val viewModel: NotesViewModel= mavericksViewModel()
    val state = viewModel.collectState()
    Text(text = state.notes.toString())
}