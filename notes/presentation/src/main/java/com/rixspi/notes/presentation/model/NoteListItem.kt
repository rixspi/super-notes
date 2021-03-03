package com.rixspi.notes.presentation.model

data class NoteListItem(
    val id: String,
    val backgroundColor: Long = 0,
    val backgroundImage: String = "",
    val childrenNotes: List<NoteListItem> = emptyList(),
    val title: String = "",
    val contentInfoListElement: List<ContentInfoListItem> = emptyList()
)