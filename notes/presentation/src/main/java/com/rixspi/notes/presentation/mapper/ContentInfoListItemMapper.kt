package com.rixspi.notes.presentation.mapper

import com.rixspi.common.domain.model.ContentInfo
import com.rixspi.notes.presentation.model.ContentInfoListItem

fun mapContentInfo(
    input: ContentInfo,
): ContentInfoListItem = with(input) {
    ContentInfoListItem(
        id = id,
        bottom = bottom,
        top = top,
        start = start,
        end = end,
        image = image ?: "",
        text = text ?: ""
    )
}