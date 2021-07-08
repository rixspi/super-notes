package com.rixspi.notes.presentation.model

import com.rixspi.domain.util.empty
import java.util.UUID
import kotlinx.serialization.SerialName

data class EditableNoteItem2(
    val id: String = UUID.randomUUID().toString(),
    val parentId: String? = null,
    val depth: Int = 0,
    val backgroundColor: Long = 0,
    @SerialName(value = "bgImage")
    val backgroundImage: String = String.empty,
    val title: String = String.empty,
    val contentInfos: List<EditableContentInfoItem> = emptyList()
)