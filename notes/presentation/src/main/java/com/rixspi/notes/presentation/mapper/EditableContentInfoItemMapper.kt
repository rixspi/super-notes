package com.rixspi.notes.presentation.mapper

import com.rixspi.common.domain.model.ContentInfo
import com.rixspi.notes.presentation.model.EditableContentInfoItem

fun EditableContentInfoItem.toContentInfo(): ContentInfo = ContentInfo(
    id = id,
    bottom = bottom,
    top = top,
    start = start,
    end = end,
    image = image,
    text = text
)
