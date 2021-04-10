package com.rixspi.notes.presentation.ui.addNote

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
        val childNote2ContentInfos =
            addNoteViewState.note.childrenNotes[1].contentInfos

        assertTrue(childNoteContentInfos.isEmpty())
        assertTrue(childNote2ContentInfos.isNotEmpty())
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

        addNoteViewState = addNoteViewState.addContentInfo(index = 1)

        val masterNoteContentInfos = addNoteViewState.note.contentInfos

        assertTrue(
            compareEditableContentInfoItemContentExceptId(
                EditableContentInfoItem(),
                masterNoteContentInfos[1]
            )
        )

        assertFalse(
            compareEditableContentInfoItemContentExceptId(
                EditableContentInfoItem(),
                masterNoteContentInfos[0]
            )
        )
        assertFalse(
            compareEditableContentInfoItemContentExceptId(
                EditableContentInfoItem(),
                masterNoteContentInfos[2]
            )
        )
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

        assertTrue(
            compareEditableContentInfoItemContentExceptId(
                EditableContentInfoItem(),
                masterNoteContentInfos[0]
            )
        )

        assertFalse(
            compareEditableContentInfoItemContentExceptId(
                EditableContentInfoItem(),
                masterNoteContentInfos[1]
            )
        )
        assertFalse(
            compareEditableContentInfoItemContentExceptId(
                EditableContentInfoItem(),
                masterNoteContentInfos[2]
            )
        )
    }

    @Test
    fun `updateContentInfo updates contentInfo at the index`() {
        addNoteViewState = addNoteViewState.copy(
            EditableNoteItem(
                contentInfos = listOf(
                    EditableContentInfoItem(text = "test1"),
                    EditableContentInfoItem(text = "test2"),
                )
            )
        )
        val newContentInfoText = "New value"

        addNoteViewState = addNoteViewState.updateContentInfo(index = 3, text = newContentInfoText)

        assertNotEquals("New value", addNoteViewState.note.contentInfos[0].text)
        assertNotEquals("New value", addNoteViewState.note.contentInfos[1].text)
    }

    @Test
    fun `updateContentInfo does not update contentInfo at the non existing index`() {
        addNoteViewState = addNoteViewState.copy(
            EditableNoteItem(
                contentInfos = listOf(
                    EditableContentInfoItem(text = "test1"),
                    EditableContentInfoItem(text = "test2"),
                )
            )
        )
        val newContentInfoText = "New value"

        addNoteViewState = addNoteViewState.updateContentInfo(index = 0, text = newContentInfoText)

        assertEquals("New value", addNoteViewState.note.contentInfos[0].text)
    }

    @Test
    fun `removeContentInfo removes contentInfo at specified index`() {
        addNoteViewState = addNoteViewState.copy(
            EditableNoteItem(
                contentInfos = listOf(
                    EditableContentInfoItem(text = "test1"),
                    EditableContentInfoItem(text = "test2"),
                    EditableContentInfoItem(text = "test3"),
                )
            )
        )

        addNoteViewState = addNoteViewState.removeContentInfo(index = 1)

        assertTrue(addNoteViewState.note.contentInfos.size == 2)
        assertTrue(addNoteViewState.note.contentInfos[0].text == "test1")
        assertTrue(addNoteViewState.note.contentInfos[1].text == "test3")
    }

    @Test
    fun `removeContentInfo does not remove contentInfo at non existing index`() {
        addNoteViewState = addNoteViewState.copy(
            EditableNoteItem(
                contentInfos = listOf(
                    EditableContentInfoItem(text = "test1"),
                    EditableContentInfoItem(text = "test2"),
                )
            )
        )

        addNoteViewState = addNoteViewState.removeContentInfo(index = 3)

        assertTrue(addNoteViewState.note.contentInfos.size == 2)
        assertTrue(addNoteViewState.note.contentInfos[0].text == "test1")
        assertTrue(addNoteViewState.note.contentInfos[1].text == "test2")
    }

    @Test
    fun `addChildrenNote adds note at specified index`() {
        addNoteViewState = addNoteViewState.copy(
            note = EditableNoteItem(
                contentInfos = listOf(
                    EditableContentInfoItem(text = "test1")
                ),
                childrenNotes = listOf(
                    EditableNoteItem(id = "id")
                )
            )
        )

        addNoteViewState = addNoteViewState.addChildrenNote(index = 0)

        assertNotEquals("id", addNoteViewState.note.childrenNotes[0].id)
        assertEquals("id", addNoteViewState.note.childrenNotes[1].id)
    }

    @Test
    fun `removeChildrenNote removes note at specified index`() {
        addNoteViewState = addNoteViewState.copy(
            note = EditableNoteItem(
                contentInfos = listOf(
                    EditableContentInfoItem(text = "test1")
                ),
                childrenNotes = listOf(
                    EditableNoteItem(id = "id")
                )
            )
        )
        assertTrue(addNoteViewState.note.childrenNotes.isNotEmpty())

        addNoteViewState = addNoteViewState.removeChildrenNote(index = 0)

        assertTrue(addNoteViewState.note.childrenNotes.isEmpty())
    }

    private fun compareEditableContentInfoItemContentExceptId(
        editableContent: EditableContentInfoItem,
        other: EditableContentInfoItem
    ) = editableContent.bottom == other.bottom &&
            editableContent.top == other.top &&
            editableContent.start == other.start &&
            editableContent.end == other.end &&
            editableContent.text == other.text &&
            editableContent.image == other.image
}
