package com.rixspi.notes.presentation.ui.addNote

import com.rixspi.notes.presentation.model.EditableNoteItem2

class NotesHandler(
    val onChangeListener: (List<EditableNoteItem2>) -> Unit = {}
) {
    private val notes: MutableMap<String, EditableNoteItem2> = mutableMapOf()
    private val children: MutableMap<String, LinkedList> = mutableMapOf()
    private val depth: MutableMap<Int, LinkedList> = mutableMapOf()

    fun appendNote(note: EditableNoteItem2) {
        notes[note.id] = note

        children.getOrPut(note.parentId ?: "ROOT") { LinkedList() }.apply { add(note.id, tail) }

        depth.getOrPut(note.depth) { LinkedList() }.apply { add(note.id, tail) }

        onChangeListener(flattenNotes())
    }

    fun removeNote(noteId: String) {
        val note = notes[noteId] ?: error("No note to remove")
        notes.remove(noteId)

        onChangeListener(flattenNotes())
    }

    private fun flattenNotes(): List<EditableNoteItem2> {
        val notesFlat = mutableListOf<EditableNoteItem2>()
        depth[0]
            ?.getAll()
            ?.forEach { noteId ->
                notesFlat.add(notes[noteId]!!)
                notesFlat.addAll(getDescendants(noteId).mapNotNull { notes[it] })
            }
        return notesFlat
    }

    private fun getDescendants(noteId: String, descendants: List<String> = emptyList()): List<String> {
        val descendantsMutable = descendants.toMutableList()
        children[noteId]
            ?.getAll()
            ?.forEach {
                descendantsMutable.add(it)
                getDescendants(it, descendantsMutable)
            }
        return descendants
    }
}
