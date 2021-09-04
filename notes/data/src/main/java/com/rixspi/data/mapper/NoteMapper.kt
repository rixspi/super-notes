package com.rixspi.data.mapper

import com.rixspi.common.domain.model.Note
import com.rixspi.data.model.NoteDto
import com.rixspi.domain.serialization.MapInput
import com.rixspi.domain.serialization.MapOutput
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
fun mapNoteDto(
    input: NoteDto
): Note = with(input) {
    // save
    val out = MapOutput()
    out.encodeSerializableValue(NoteDto.serializer(), input)
    // load
    val inp = MapInput(out.map)
    val other = inp.decodeSerializableValue(Note.serializer())
    return other
}
