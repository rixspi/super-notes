package com.rixspi.notes.presentation.ui.addNote

import com.rixspi.notes.presentation.model.EditableNoteItem2

class NotesHandler(
    val onChangeListener: (List<EditableNoteItem2>) -> Unit = {}
) {
    private val notes: MutableMap<String, EditableNoteItem2> = mutableMapOf()
    private val children: MutableMap<String, LinkedList> = mutableMapOf()
    private val depth: MutableMap<Int, LinkedList> = mutableMapOf()
    private val positions: MutableMap<String, Int> = mutableMapOf()

    fun appendNote(note: EditableNoteItem2) {
        notes[note.id] = note

        children.getOrPut(note.parentId ?: "ROOT") { LinkedList() }.add(note.id)

        //positions[note.id] = calculateMiddle(getLastSiblingPosition(note.id), 0)

        depth.getOrPut(note.depth) { LinkedList() }.add(note.id)


        onChangeListener(flattenNotes())
    }

    fun removeNote(noteId: String) {
        val note = notes[noteId] ?: error("No note to remove")
        notes.remove(noteId)

        onChangeListener(flattenNotes())
    }

    fun getSiblings(noteId: String): List<String> = depth[notes[noteId]?.depth]?.getAll() ?: emptyList()

    fun getLastSiblingPosition(noteId: String): Int {
        val siblingsPosition = getSiblings(noteId).map { positions[it] }
        val lastSiblingPosition = siblingsPosition.maxByOrNull { it ?: 0 }
        return lastSiblingPosition ?: 0
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

    // Not optimal, I can store already sorted notes, so there won't be a need to resort every time
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

    private val positionStep = 10000
    private fun calculateMiddle(start: Int, end: Int): Int {
        if (start == 0) {
            return positionStep
        }

        if (end == 0) {
            return start + positionStep
        }

        return start + end / 2
    }
}