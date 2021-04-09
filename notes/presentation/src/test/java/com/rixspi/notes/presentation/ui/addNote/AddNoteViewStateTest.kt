package com.rixspi.notes.presentation.ui.addNote

import com.rixspi.common.domain.model.ContentInfo
import com.rixspi.common.domain.model.Note
import com.rixspi.notes.presentation.model.EditableContentInfoItem
import com.rixspi.notes.presentation.model.EditableNoteItem
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class AddNoteViewStateTest {
    private lateinit var addNoteViewState: AddNoteViewState

    @Before
    fun setup() {
        addNoteViewState = AddNoteViewState()
    }

    @Test
    fun `addContentInfo adds empty contentInfo to the specified note`() {
        val childNote1 = EditableNoteItem()
        val childNote2 = EditableNoteItem()
        addNoteViewState = addNoteViewState.copy(
            EditableNoteItem(
                childrenNotes = listOf(
                    childNote1,
                    childNote2
                )
            )
        )

        addNoteViewState = addNoteViewState.addContentInfo(childNote2)

        val childNoteContentInfos =
            addNoteViewState.note.childrenNotes[0].contentInfos

        assertTrue(childNoteContentInfos.isNotEmpty())
    }

    @Test
    fun `addContentInfo adds empty contentInfo to the master note if note not specified`() {
        addNoteViewState = addNoteViewState.addContentInfo()

        val masterNoteContentInfos = addNoteViewState.note.contentInfos

        assertTrue(masterNoteContentInfos.size > 1)
    }

    @Test
    fun `addContentInfo adds empty contentInfo at the specified index`() {
        addNoteViewState = addNoteViewState.copy(
            EditableNoteItem(
                contentInfos = listOf(
                    EditableContentInfoItem(text = "test1"),
                    EditableContentInfoItem(text = "test2"),
                )
            )
        )

        addNoteViewState = addNoteViewState.addContentInfo(indexOfCurrentContentInfo = 1)

        val masterNoteContentInfos = addNoteViewState.note.contentInfos

        // TODO I've added the class for Editable note and content info which autogenerates the UUID
        //  this is not yet reflected in here ;)

        assertEquals(EditableContentInfoItem(), masterNoteContentInfos[1])
        assertNotEquals(EditableContentInfoItem(), masterNoteContentInfos[0])
        assertNotEquals(EditableContentInfoItem(), masterNoteContentInfos[2])
    }

    @Test
    fun `addContentInfo adds empty contentInfo at the beginning if index not specified`() {
        addNoteViewState = addNoteViewState.copy(
            EditableNoteItem(
                contentInfos = listOf(
                    EditableContentInfoItem(text = "test1"),
                    EditableContentInfoItem(text = "test2"),
                )
            )
        )

        addNoteViewState = addNoteViewState.addContentInfo()

        val masterNoteContentInfos = addNoteViewState.note.contentInfos

        assertEquals(EditableContentInfoItem(), masterNoteContentInfos[0])
        assertNotEquals(EditableContentInfoItem(), masterNoteContentInfos[1])
        assertNotEquals(EditableContentInfoItem(), masterNoteContentInfos[2])
    }

}
