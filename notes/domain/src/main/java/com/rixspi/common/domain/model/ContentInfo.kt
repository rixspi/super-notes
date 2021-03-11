package com.rixspi.common.domain.model

import com.rixspi.domain.util.empty
import kotlinx.serialization.Serializable

@Serializable
data class ContentInfo(
    val id: String = String.empty,
    val bottom: Int = 0,
    val top: Int = 0,
    val start: Int = 0,
    val end: Int = 0,
    val image: String? = null,
    val text: String? = null
)