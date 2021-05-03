package com.rixspi.data.model

import com.rixspi.domain.util.empty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    val id: String = String.empty,
    @SerialName(value = "bgColor")
    val backgroundColor: Long = 0,
    @SerialName(value = "bgImage")
    val backgroundImage: String = String.empty,
    val title: String = String.empty,
    val childrenNotes: List<NoteDto> = emptyList(),
    val contentInfos: List<ContentInfoDto> = emptyList()
)
