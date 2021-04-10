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

        addNoteViewState = addNoteViewState.addContentInfo(indexOfCurrentContentInfo = 1)

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
