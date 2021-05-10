package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.MavericksState
import com.rixspi.notes.presentation.model.EditableContentInfoItem
import com.rixspi.notes.presentation.model.EditableNoteItem2
import java.util.UUID

private const val ROOT = "ROOT"

data class AddNoteViewState2(
    val notes: Map<String, EditableNoteItem2> = mapOf(
        ROOT to EditableNoteItem2(
            id = UUID.randomUUID().toString(),
            position = "0",
            contentInfos = listOf(EditableContentInfoItem())
        )
    ),
    val added: Boolean = false
) : MavericksState {
    private fun getRootNote() = notes[ROOT] ?: error("Root note has to be defined first!")

    fun setTitle(note: EditableNoteItem2 = getRootNote(), title: String): AddNoteViewState2 {
        val noteWithNewTitle = note.copy(title = title)

        return copy(notes = notes.copy(ROOT to noteWithNewTitle))
    }

    fun addContentInfo(
        note: EditableNoteItem2 = getRootNote(),
        id: String,
        index: Int = 0
    ): AddNoteViewState2 {

        return this
    }

    fun updateContentInfo(
        note: EditableNoteItem2 = getRootNote(),
        index: Int,
        text: String,
    ): AddNoteViewState2 {

        return this
    }

    fun removeContentInfo(
        note: EditableNoteItem2 = getRootNote(),
        index: Int = 0
    ): AddNoteViewState2 {

        return this
    }

    fun addChildrenNote(
        parentNote: EditableNoteItem2 = getRootNote(),
        id: String
    ): AddNoteViewState2 {

        // position has to always be one behind the parent, so I need a method which will calculate that

        return copy(notes = notes.copy(id to EditableNoteItem2(id = id, parentId = parentNote.id, position = "1000")))
    }

    fun removeChildrenNote(
        note: EditableNoteItem2 = getRootNote(),
        index: Int = 0
    ): AddNoteViewState2 {

        return this
    }

    private fun <T> List<T>.modify(
        action: MutableList<T>.() -> Unit
    ): List<T> = this
        .toMutableList()
        .apply {
            action(this)
        }
        .toList()

    private fun prepareNewState(
        note: EditableNoteItem2,
        modifiedNote: EditableNoteItem2
    ): AddNoteViewState2 {

        return this
    }
}

/**
 * Returns a new immutable map with the provided keys set/updated.
 */
fun <K, V> Map<K, V>.copy(vararg pairs: Pair<K, V>) = HashMap<K, V>(size + pairs.size).apply {
    putAll(this@copy)
    pairs.forEach { put(it.first, it.second) }
}

/**
 * Returns a new map with the provided keys removed.
 */
fun <K, V> Map<K, V>.delete(vararg keys: K): Map<K, V> {
    // This should be slightly more efficient than Map.filterKeys because we start with a map of the
    // correct size rather than a growing LinkedHashMap.
    return HashMap<K, V>(size - keys.size).apply {
        this@delete.entries.asSequence()
            .filter { it.key !in keys }
            .forEach { put(it.key, it.value) }
    }
}
