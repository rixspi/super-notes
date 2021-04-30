package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.Mavericks
import com.rixspi.common.domain.interactors.CreateNote
import com.rixspi.domain.util.empty
import com.rixspi.notes.presentation.mapper.toNote
import com.rixspi.notes.presentation.model.EditableContentInfoItem
import com.rixspi.notes.presentation.model.EditableNoteItem
import com.rixspi.notes.presentation.ui.MainCoroutineRule
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @get:Rule
    val coroutineRule = MainCoroutineRule()

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
            val stateBeforeAdd = addNoteViewModel.awaitState()
            addNoteViewModel.addNote(parentNote = stateBeforeAdd.note)

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

            addNoteViewModel.addNote(parentNote = editableNote, index = 1)

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

            addNoteViewModel.removeNote(parentNote = editableNote, index = 0)

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

    @Test
    fun `updateContentInfo changes the specified content info`() {
        runBlocking {
            val editableNote = EditableNoteItem(
                contentInfos = listOf(
                    EditableContentInfoItem(text = "Not changed"),
                    EditableContentInfoItem(text = "Not changed"),
                    EditableContentInfoItem(text = "Not changed")
                )
            )

            val addNoteViewState = AddNoteViewState(editableNote)

            addNoteViewModel = AddNoteViewModel(
                addNoteViewState,
                createNote
            )

            addNoteViewModel.updateContentInfo(note = editableNote, index = 0, text = "Changed")

            val stateAfterTitleChange = addNoteViewModel.awaitState()
            compareEditableContentInfoItemContentExceptId(
                EditableContentInfoItem("Changed"),
                stateAfterTitleChange.note.contentInfos[0]
            )
            compareEditableContentInfoItemContentExceptId(
                EditableContentInfoItem("Not changed"),
                stateAfterTitleChange.note.contentInfos[1]
            )
        }
    }

    @Test
    fun `removeContentInfo removes content info at the specified index`() {
        runBlocking {
            val editableNote = EditableNoteItem(
                contentInfos = listOf(
                    EditableContentInfoItem(text = "Not removed"),
                    EditableContentInfoItem(text = "Removed"),
                    EditableContentInfoItem(text = "Not removed")
                )
            )

            val addNoteViewState = AddNoteViewState(editableNote)

            addNoteViewModel = AddNoteViewModel(
                addNoteViewState,
                createNote
            )

            addNoteViewModel.removeContentInfo(note = editableNote, index = 1)

            val stateAfterTitleChange = addNoteViewModel.awaitState()
            compareEditableContentInfoItemContentExceptId(
                EditableContentInfoItem("Not removed"),
                stateAfterTitleChange.note.contentInfos[0]
            )
            compareEditableContentInfoItemContentExceptId(
                EditableContentInfoItem("Not removed"),
                stateAfterTitleChange.note.contentInfos[1]
            )
        }
    }

    @Test
    fun `createNote calls CreateNote use case`() {
        runBlocking {
            val editableNote = EditableNoteItem(
                contentInfos = listOf(
                    EditableContentInfoItem(text = "Not removed"),
                    EditableContentInfoItem(text = "Removed"),
                    EditableContentInfoItem(text = "Not removed")
                )
            )

            val addNoteViewState = AddNoteViewState(editableNote)

            addNoteViewModel = AddNoteViewModel(
                addNoteViewState,
                createNote
            )

            addNoteViewModel.createNote()

            coVerify { createNote.invoke(CreateNote.Params(addNoteViewState.note.toNote())) }
        }
    }
}