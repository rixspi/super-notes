package com.rixspi.data.mapper

import com.rixspi.common.domain.model.ContentInfo
import com.rixspi.common.domain.model.Note
import com.rixspi.data.model.ContentInfoDto
import com.rixspi.data.model.NoteDto

fun mapNoteDto(
    input: NoteDto,
    contentInfoMapper: (List<ContentInfoDto>) -> List<ContentInfo>
): Note = with(input) {
    Note(
        id = id,
        backgroundColor = backgroundColor,
        backgroundImage = backgroundImage,
        title = title,
        childrenNotes = childrenNotes.map { mapNoteDto(it, contentInfoMapper) },
        contentInfos = contentInfoMapper(contentInfos)
    )
}
