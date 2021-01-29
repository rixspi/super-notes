package com.rixspi.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: String = "",
    @SerialName(value = "bgColor")
    val backgroundColor: Long = 0,
    @SerialName(value = "bgImage")
    val backgroundImage: String = "",
    val title: String = "",
    val childrenNotes: List<Note> = emptyList(),
    val contentInfos: List<ContentInfo> = emptyList()
)