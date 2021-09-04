package com.rixspi.notes.presentation.model

import com.rixspi.domain.util.empty

data class NoteListItem(
    val id: String,
    val parentId: String? = null,
    val depth: Int = 0,
    val backgroundColor: Long = 0,
    val backgroundImage: String = String.empty,
    val childrenNotes: List<NoteListItem> = emptyList(),
    val title: String = String.empty,
    val bottom: Int = 0,
    val top: Int = 0,
    val start: Int = 0,
    val end: Int = 0,
    val image: String? = null,
    val text: String? = null
)
