package com.rixspi.notes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.InternalMavericksApi
import com.rixspi.common.presentation.mvrx.collectState
import com.rixspi.common.presentation.mvrx.mavericksViewModel


@Composable
fun AddNote() {
    val viewModel: AddNoteViewModel = mavericksViewModel()
    val state = viewModel.collectState()
    Column() {
        TextInput(label = "Note title")
        TextInput(label = "Note content")
    }
}

@Composable
fun TextInput(
    label: String,
    onChange: (String) -> Unit = {}
) {
    val (text, setText) = remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        onValueChange = {
            setText(it)
            onChange(it.text)
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    )
}