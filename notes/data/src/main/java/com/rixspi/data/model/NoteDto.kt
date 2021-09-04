package com.rixspi.data.model

import com.rixspi.domain.util.empty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    val id: String = String.empty,
    val parentId: String? = null,
    val depth: Int = 0,
    @SerialName(value = "bgColor")
    val backgroundColor: Long = 0,
    @SerialName(value = "bgImage")
    val backgroundImage: String = String.empty,
    val title: String = String.empty,
    val childrenNotes: List<NoteDto> = emptyList(),
    val bottom: Int = 0,
    val top: Int = 0,
    val start: Int = 0,
    val end: Int = 0,
    val image: String? = null,
    val text: String? = null
)
