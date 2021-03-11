package com.rixspi.notes.presentation.mapper

import com.rixspi.common.domain.model.ContentInfo
import com.rixspi.domain.util.empty
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
        image = image ?: String.empty,
        text = text ?: String.empty
    )
}