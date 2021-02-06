package com.rixspi.notes.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.mvrx.InternalMavericksApi
import com.rixspi.common.presentation.mvrx.collectState
import com.rixspi.common.presentation.mvrx.mavericksViewModel


@OptIn(InternalMavericksApi::class)
@Composable
@Preview
fun MyComposable() {
    val viewModel: NotesViewModel= mavericksViewModel()
    val state = viewModel.collectState()
    Text(text = state.notes.toString())
}