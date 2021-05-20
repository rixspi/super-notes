package com.rixspi.notes.presentation.ui.addNote

import com.rixspi.notes.presentation.model.EditableNoteItem
import com.rixspi.notes.presentation.model.EditableNoteItem2
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AddNoteViewStateTest2 {
    private lateinit var addNoteViewState: AddNoteViewState2

    @Before
    fun setup() {
        addNoteViewState = AddNoteViewState2()
    }

    @Test
    fun `setTitle sets the title on the default note if not specified`() {
        addNoteViewState = addNoteViewState.setTitle(title = "New title")

        assertEquals("New title", addNoteViewState.notes["ROOT"]!!.title)
    }

    @Test
    fun `setTitle sets the title on the specified note`() {
        val childNote1 = EditableNoteItem2()
        val childNote2 = EditableNoteItem2()
    }

    @Test
    fun `addContentInfo adds empty contentInfo to the specified note`() {
        val childNote1 = EditableNoteItem()
        val childNote2 = EditableNoteItem()
    }

    @Test
    fun `addContentInfo adds empty contentInfo to the master note if note not specified`() {
    }

    @Test
    fun `addContentInfo adds empty contentInfo at the specified index`() {
    }

    @Test
    fun `addContentInfo adds empty contentInfo at the beginning if index not specified`() {
    }

    @Test
    fun `updateContentInfo updates contentInfo at the index`() {
    }

    @Test
    fun `updateContentInfo does not update contentInfo at the non existing index`() {
    }

    @Test
    fun `removeContentInfo removes contentInfo at specified index`() {
    }

    @Test
    fun `removeContentInfo does not remove contentInfo at non existing index`() {
    }

    @Test
    fun `addChildrenNote adds note right behind the parent`() {
        val id = "CHILD_LEVEL_1"
        addNoteViewState = addNoteViewState.addChildrenNote(id = id)

        val expectedPosition = 0

        assertEquals(expectedPosition.toString(), addNoteViewState.notes[id]?.position)
    }

    @Test
    fun `addChildrenNote adds note right behind the parent when there is more than one`() {
        val id = "CHILD_LEVEL_1"
        val id2 = "CHILD_LEVEL_1_POSITION_2"
        addNoteViewState = addNoteViewState.addChildrenNote(id = id)
        addNoteViewState = addNoteViewState.addChildrenNote(id = id2)

        // TODO prepare a function which will handle creating this positions
        //  for now finding the middle between numbers is suffice, with strings I can go to fractions
        val expectedPosition = 10000

        assertEquals(expectedPosition, addNoteViewState.notes[id2]?.position)
    }

    @Test
    fun `addChildrenNote to children note and childten note and so on`() {
    }

    @Test
    fun `removeChildrenNote removes note at specified index`() {
    }
}
