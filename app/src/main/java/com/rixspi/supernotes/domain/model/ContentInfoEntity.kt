package com.rixspi.supernotes.domain.model

import com.google.firebase.firestore.DocumentReference
import kotlinx.serialization.Serializable

@Serializable
data class ContentInfoEntity(
    val id: String = "",
    val bottom: Int = 0,
    val top: Int = 0,
    val start: Int = 0,
    val end: Int = 0,
    val image: String? = null,
    val text: String? = null
)