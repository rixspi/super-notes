package com.rixspi.notes.presentation.ui.addNote

import com.rixspi.notes.presentation.model.EditableContentInfoItem
import com.rixspi.notes.presentation.model.EditableNoteItem2

class NotesHandler {
    companion object {
        private const val ROOT = "ROOT"
    }

    private val notes: MutableMap<String, EditableNoteItem2> = mutableMapOf()
    private val children: MutableMap<String, LinkedList> = mutableMapOf()
    private val depth: MutableMap<Int, LinkedList> = mutableMapOf()
    private var flatten: List<EditableNoteItem2> = emptyList()

    fun appendNote(note: EditableNoteItem2) {
        val parent = notes[note.parentId]
        val depthNew = parent?.depth ?: 0 + 1
        notes[note.id] = note.copy(depth = depthNew)

        children.getOrPut(note.parentId ?: ROOT) { LinkedList() }.apply { add(note.id, tail) }

        depth.getOrPut(depthNew) { LinkedList() }.apply { add(note.id, tail) }

        // This pretty much defies the idea of having fast operations :D
        flatten = flattenNotes()
    }

    fun removeNote(noteId: String) {
        notes.remove(noteId)
        children.remove(noteId)
        flatten = flattenNotes()
    }

    fun setTitle(noteId: String, title: String) {
        notes[noteId] = notes[noteId]?.copy(title = title)!!
        flatten = flattenNotes()
    }

    fun addContentInfo(noteId: String, contentInfoItemId: String) {
        val note = notes[noteId] ?: return
        val contentInfos = note.contentInfos
        if (contentInfos.firstOrNull { it.id == contentInfoItemId } == null) {
            val contentInfosMutable =
                contentInfos.toMutableList().apply { add(EditableContentInfoItem(id = contentInfoItemId)) }
            notes[noteId] = note.copy(contentInfos = contentInfosMutable.toList())
        }
        flatten = flattenNotes()
    }

    fun getOrderedList(): List<EditableNoteItem2> = flatten.map {
        val contents = it.contentInfos.map { content -> content.copy() }
        it.copy(contentInfos = contents.toMutableList())
    }.toMutableList()

    // After adding a note on depth 1 no notes are retrieved
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
