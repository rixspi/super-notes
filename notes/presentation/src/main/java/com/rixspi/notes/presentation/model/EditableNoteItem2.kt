package com.rixspi.notes.presentation.model

import com.rixspi.domain.util.empty
import java.util.UUID
import kotlinx.serialization.SerialName

data class EditableNoteItem2(
    val id: String = UUID.randomUUID().toString(),
    val parentId: String? = null,
    val depth: Int = 0,
    var backgroundColor: Long = 0,
    @SerialName(value = "bgImage")
    var backgroundImage: String = String.empty,
    var title: String = String.empty,
    val contentInfos: MutableList<EditableContentInfoItem> = mutableListOf()
)