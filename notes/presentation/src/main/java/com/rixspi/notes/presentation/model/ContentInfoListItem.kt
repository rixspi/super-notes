package com.rixspi.notes.presentation.model

import com.rixspi.domain.util.empty

data class ContentInfoListItem(
    val id: String,
    val text: String = String.empty,
    val image: String = String.empty,
    val bottom: Int = 0,
    val top: Int = 0,
    val start: Int = 0,
    val end: Int = 0
)
