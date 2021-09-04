package com.rixspi.notes.presentation.model

import com.rixspi.domain.util.empty
import java.util.UUID
import kotlinx.serialization.SerialName

data class EditableNoteItem(
    val id: String = UUID.randomUUID().toString(),
    val parentId: String? = null,
    val depth: Int = 0,
    val backgroundColor: Long = 0,
    @SerialName(value = "bgImage")
    val backgroundImage: String = String.empty,
    val title: String = String.empty,
    val bottom: Int = 0,
    val top: Int = 0,
    val start: Int = 0,
    val end: Int = 0,
    val image: String? = null,
    val text: String? = null
)