package com.rixspi.notes.presentation.model

import java.util.*

data class EditableContentInfoItem(
    val id: String = UUID.randomUUID().toString(),
    val bottom: Int = 0,
    val top: Int = 0,
    val start: Int = 0,
    val end: Int = 0,
    val image: String? = null,
    val text: String? = null
)
