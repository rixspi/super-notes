package com.rixspi.notes.presentation.model

import com.rixspi.domain.util.empty
import java.util.*
import kotlinx.serialization.SerialName

data class EditableNoteItem(
    val id: String = UUID.randomUUID().toString(),
    val backgroundColor: Long = 0,
    @SerialName(value = "bgImage")
    val backgroundImage: String = String.empty,
    val title: String = String.empty,
    val childrenNotes: List<EditableNoteItem> = emptyList(),
    val contentInfos: List<EditableContentInfoItem> = emptyList()
)
