package com.rixspi.notes.presentation.ui.addNote

import com.rixspi.notes.presentation.model.EditableNoteItem2
import org.junit.Assert.assertEquals
import org.junit.Test

class NotesHandlerTest {

    private var notes: List<EditableNoteItem2> = emptyList()
    private val notesHandler = NotesHandler {
        notes = it
    }

    @Test
    fun `appendNote should add note at the last position`() {
        val parentId = "parent"
        val note = EditableNoteItem2(parentId = parentId)

        notesHandler.appendNote(note)

        val createdNote = notes.firstOrNull { it.id == note.id }
        assertEquals(parentId, createdNote?.parentId)
        assertEquals(notes.lastIndex, notes.indexOf(createdNote))
    }

    @Test
    fun `appendNote should set proper positions`() {
        val parentId = "parent"
        val note = EditableNoteItem2(parentId = parentId)
        val note2 = EditableNoteItem2(parentId = parentId)
        val note3 = EditableNoteItem2(parentId = parentId)

        notesHandler.appendNote(note)
        notesHandler.appendNote(note2)
        notesHandler.appendNote(note3)

        val createdNote = notes.firstOrNull { it.id == note.id }
        val createdNote2 = notes.firstOrNull { it.id == note2.id }
        val createdNote3 = notes.firstOrNull { it.id == note3.id }

        assertEquals(0, notes.indexOf(createdNote))
        assertEquals(1, notes.indexOf(createdNote2))
        assertEquals(2, notes.indexOf(createdNote3))
    }

    @Test
    fun `removeNote removes note from the handler maps`() {
    }

    @Test
    fun `removeNote removes children notes from the handler maps`() {
    }
}
