package com.rixspi.data.mapper

import com.rixspi.data.model.ContentInfoDto
import com.rixspi.domain.model.ContentInfo

fun mapContentInfoDto(
    input: ContentInfoDto,
): ContentInfo = with(input) {
    ContentInfo(
        id = id, bottom = bottom, top = top, start = start, end = end, image = image, text = text
    )
}