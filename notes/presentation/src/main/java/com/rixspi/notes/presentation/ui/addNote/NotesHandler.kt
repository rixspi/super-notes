package com.rixspi.notes.presentation.ui.addNote

import com.rixspi.notes.presentation.model.EditableNoteItem
import com.rixspi.notes.presentation.model.EditableNoteItem2

private const val ROOT = "ROOT"

class NotesHandler {
    private val notes: MutableMap<String, EditableNoteItem2> = mutableMapOf()
    private val children: MutableMap<String, LinkedList> = mutableMapOf()
    private val depth: MutableMap<Int, LinkedList> = mutableMapOf()
    private var flatten: List<EditableNoteItem2> = emptyList()

    fun appendNote(note: EditableNoteItem2) {
        notes[note.id] = note

        children.getOrPut(note.parentId ?: ROOT) { LinkedList() }.apply { add(note.id, tail) }

        depth.getOrPut(note.depth) { LinkedList() }.apply { add(note.id, tail) }

        flatten = flattenNotes()
    }

    fun removeNote(noteId: String) {
        notes.remove(noteId)
        children.remove(noteId)
        flatten = flattenNotes()
    }

    fun setTitle(noteId: String, title: String) {
        notes[noteId]?.title = title
    }

    fun getOrderedList(): List<EditableNoteItem2> = flatten.map {
        val contents = it.contentInfos.map { content -> content.copy() }

        it.copy(contentInfos = contents.toMutableList())
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

    fun getNote(): EditableNoteItem {
        val root = children[ROOT]?.head
        val note = notes[root]!!
        // EditableNoteItem(
        //     id = note.id,
        //     backgroundColor = note.backgroundColor,
        //     backgroundImage = note.backgroundImage,
        //     title = note.title,
        //     ...
        //     //T ODO
        // )
    }
}
