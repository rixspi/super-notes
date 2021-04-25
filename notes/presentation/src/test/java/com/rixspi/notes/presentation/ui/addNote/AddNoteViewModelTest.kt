package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.Mavericks
import com.rixspi.common.domain.interactors.CreateNote
import com.rixspi.domain.util.empty
import com.rixspi.notes.presentation.model.EditableContentInfoItem
import com.rixspi.notes.presentation.model.EditableNoteItem
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class AddNoteViewModelTest {
    private lateinit var addNoteViewModel: AddNoteViewModel
    private val createNote: CreateNote = mockk(relaxed = true)


    @Before
    fun setup() {
        Mavericks.initialize(debugMode = true)
        addNoteViewModel = AddNoteViewModel(
            AddNoteViewState(),
            createNote
        )
    }

    @Test
    fun `addNote adds new empty note to the children notes`() {
        runBlocking {
            addNoteViewModel.addNote()

            val state = addNoteViewModel.awaitState()
            assertTrue(state.note.childrenNotes.isNotEmpty())
        }
    }

    @Test
    fun `addNote adds new empty note at specified index`() {
        runBlocking {
            val editableNote = EditableNoteItem(
                childrenNotes = listOf(
                    EditableNoteItem(title = "test"),
                    EditableNoteItem(title = "test")
                )
            )

            val addNoteViewState = AddNoteViewState(editableNote)

            addNoteViewModel = AddNoteViewModel(
                addNoteViewState,
                createNote
            )

            addNoteViewModel.addNote(index = 1)

            val stateAfterAddWithIndex = addNoteViewModel.awaitState()

            assertEquals(stateAfterAddWithIndex.note.childrenNotes[1].title, String.empty)
            assertEquals(stateAfterAddWithIndex.note.childrenNotes[0].title, "test")
            assertEquals(stateAfterAddWithIndex.note.childrenNotes[2].title, "test")
        }
    }

    @Test
    fun `removeNote removes the specified note`() {
        runBlocking {
            val editableNote = EditableNoteItem(
                childrenNotes = listOf(
                    EditableNoteItem(title = "test1"),
                    EditableNoteItem(title = "test2")
                )
            )

            val addNoteViewState = AddNoteViewState(editableNote)

            addNoteViewModel = AddNoteViewModel(
                addNoteViewState,
                createNote
            )

            addNoteViewModel.removeNote(0)

            val stateAfterRemove = addNoteViewModel.awaitState()

            assertEquals("test2", stateAfterRemove.note.childrenNotes[0].title)
        }
    }

    @Test
    fun `setTitle sets title of the specified note`() {
        runBlocking {
            val editableNote = EditableNoteItem(
                childrenNotes = listOf(
                    EditableNoteItem(title = "test1"),
                    EditableNoteItem(title = "test2")
                )
            )

            val addNoteViewState = AddNoteViewState(editableNote)

            addNoteViewModel = AddNoteViewModel(
                addNoteViewState,
                createNote
            )

            addNoteViewModel.updateTitle(editableNote.childrenNotes[0], "New title")

            val stateAfterTitleChange = addNoteViewModel.awaitState()
            assertEquals("New title", stateAfterTitleChange.note.childrenNotes[0].title)
        }
    }

    @Test
    fun `addContentInfo adds content info in the specified note`() {
        runBlocking {
            val editableNote = EditableNoteItem(
                childrenNotes = listOf(
                    EditableNoteItem(title = "test1"),
                    EditableNoteItem(title = "test2")
                )
            )

            val addNoteViewState = AddNoteViewState(editableNote)

            addNoteViewModel = AddNoteViewModel(
                addNoteViewState,
                createNote
            )

            addNoteViewModel.addContent(editableNote.childrenNotes[0], index = 0)

            val stateAfterTitleChange = addNoteViewModel.awaitState()
            compareEditableContentInfoItemContentExceptId(
                EditableContentInfoItem(),
                stateAfterTitleChange.note.childrenNotes[0].contentInfos[0]
            )
        }
    }
}