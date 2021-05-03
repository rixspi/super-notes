package com.rixspi.notes.presentation.model

import com.rixspi.domain.util.empty

data class NoteListItem(
    val id: String,
    val backgroundColor: Long = 0,
    val backgroundImage: String = String.empty,
    val childrenNotes: List<NoteListItem> = emptyList(),
    val title: String = String.empty,
    val contentInfoListElement: List<ContentInfoListItem> = emptyList()
)
