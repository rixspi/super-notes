package com.rixspi.data.mapper

import com.rixspi.common.domain.model.ContentInfo
import com.rixspi.data.model.ContentInfoDto

fun mapContentInfoDto(
    input: ContentInfoDto,
): ContentInfo = with(input) {
    ContentInfo(
        id = id, bottom = bottom, top = top, start = start, end = end, image = image, text = text
    )
}
