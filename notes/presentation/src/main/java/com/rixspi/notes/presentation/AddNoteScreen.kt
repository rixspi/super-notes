package com.rixspi.notes.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.rixspi.domain.util.empty


@Composable
fun AddNoteScreen(
    noteAdded: () -> Unit
) {
    val viewModel: AddNoteViewModel = mavericksViewModel()
    val state = viewModel.collectAsState()

    val note = state.value.note

    if (state.value.added) {
        noteAdded()
    }

    Scaffold(
        floatingActionButton = {
            FabButtonView {
                viewModel.createNote()
            }
        }
    ) {
        LazyColumn {
            item {
                TextInput(initText = note.title, label = "Note title") {
                    viewModel.updateTitle(title = it)
                }
            }
            items(count = note.contentInfos.size + 1, itemContent = { index ->
                if (index == note.contentInfos.size) {
                    Row {
                        Button(
                            onClick = { viewModel.addContent() },
                            colors = ButtonDefaults
                                .textButtonColors()
                        ) {
                            Text("Add")
                        }
                        Button(
                            onClick = { viewModel.removeContentInfo() },
                            colors = ButtonDefaults
                                .textButtonColors()
                        ) {
                            Text("Remove")
                        }
                    }
                } else {
                    val contentInfo = note.contentInfos[index].text ?: String.empty
                    TextInput(initText = contentInfo, label = "Note content") {
                        viewModel.updateContentInfo(index = index, text = it)
                    }
                }
            })
        }
    }
}

@Composable
fun TextInput(
    label: String,
    initText: String = String.empty,
    onChange: (String) -> Unit = {}
) {
    val (text, setText) = remember { mutableStateOf(TextFieldValue(initText)) }
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