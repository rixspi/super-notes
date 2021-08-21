package com.rixspi.notes.presentation.ui.addNote

import com.rixspi.notes.presentation.model.EditableContentInfoItem
import com.rixspi.notes.presentation.model.EditableNoteItem2
import java.util.Stack

class NotesHandler {
    companion object {
        private const val ROOT = "ROOT"
    }

    private val notes: MutableMap<String, EditableNoteItem2> = mutableMapOf()
    private val children: MutableMap<String, LinkedList> = mutableMapOf()
    // TODO Move contentInfos from notesList and store them in one hash map, then the notesOrder can become one place
    //  to store ordering, it will make removing contentINfos easier as right now it has to be implemented differently
    private val notesOrder = LinkedList()

    fun appendNote(note: EditableNoteItem2) {
        val parent = notes[note.parentId]
        val depthNew = (parent?.depth ?: -1) + 1
        notes[note.id] = note.copy(depth = depthNew)

        notesOrder.add(note.id, parent?.id)

        children.getOrPut(note.parentId ?: ROOT) { LinkedList() }.apply { add(note.id, tail) }
    }

    fun removeNote(noteId: String) {
        notesOrder.remove(noteId)
        getDescendants(noteId).forEach {
            notesOrder.remove(it)
            notes.remove(it)
        }

        notes.remove(noteId)
    }

    fun setTitle(noteId: String, title: String) {
        notes[noteId] = notes[noteId]?.copy(title = title)!!
    }

    fun addContentInfo(noteId: String, contentInfoItemId: String) {
        val note = notes[noteId] ?: return
        val contentInfos = note.contentInfos
        if (contentInfos.firstOrNull { it.id == contentInfoItemId } == null) {
            val contentInfosMutable =
                contentInfos.toMutableList().apply { add(EditableContentInfoItem(id = contentInfoItemId)) }
            notes[noteId] = note.copy(contentInfos = contentInfosMutable.toList())
        }
    }

    fun getOrderedList(): List<EditableNoteItem2> = notesOrder.getAll().map { notes[it]!! }.map {
        val contents = it.contentInfos.map { content -> content.copy() }
        it.copy(contentInfos = contents.toMutableList())
    }.toMutableList()

    private fun getDescendants(noteId: String): List<String> {
        val nodesToVisit = Stack<String>().apply {
            addAll(children[noteId]?.getAll() ?: emptyList())
        }

        val descendants = mutableListOf<String>()
        while (nodesToVisit.isNotEmpty()) {
            val firstNode = nodesToVisit.pop()
            nodesToVisit.addAll(children[firstNode]?.getAll() ?: emptyList())
            descendants.add(firstNode)
        }
        return descendants
    }
}
