package com.rixspi.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    val id: String = "",
    @SerialName(value = "bgColor")
    val backgroundColor: Long = 0,
    @SerialName(value = "bgImage")
    val backgroundImage: String = "",
    val childrenNotes: List<NoteDto> = emptyList(),
    val contentInfos: List<ContentInfoDto> = emptyList()
)