package com.rixspi.supernotes.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteEntity(
    val id: String = "",
    @SerialName(value = "bgColor")
    val backgroundColor: Long = 0,
    @SerialName(value = "bgImage")
    val backgroundImage: String = "",
    val childrenNotes: List<NoteEntity> = emptyList(),
    val contentInfos: List<ContentInfoEntity> = emptyList()
)