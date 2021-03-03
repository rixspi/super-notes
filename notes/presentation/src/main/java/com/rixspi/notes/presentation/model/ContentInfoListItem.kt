package com.rixspi.notes.presentation.model

data class ContentInfoListItem(
    val id: String,
    val text: String = "",
    val image: String = "",
    val bottom: Int = 0,
    val top: Int = 0,
    val start: Int = 0,
    val end: Int = 0
)