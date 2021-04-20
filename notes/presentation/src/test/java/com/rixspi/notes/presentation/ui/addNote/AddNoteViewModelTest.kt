package com.rixspi.notes.presentation.ui.addNote

import com.airbnb.mvrx.Mavericks
import com.rixspi.common.domain.interactors.CreateNote
import com.rixspi.domain.util.empty
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
            addNoteViewModel.addNote()
            addNoteViewModel.addNote()


            val state = addNoteViewModel.awaitState()

            // TODO setTitle in AddNoteViewState is not prepared for passing the specific note,
            //  create a unit test for that and follow with the implementation
            addNoteViewModel.updateTitle(state.note.childrenNotes[0], "test")
            addNoteViewModel.updateTitle(state.note.childrenNotes[1], "test")

            addNoteViewModel.addNote(index = 1)

            val stateAfterAddWithIndex = addNoteViewModel.awaitState()

            assertTrue(stateAfterAddWithIndex.note.childrenNotes[1].title == String.empty)
            assertEquals(stateAfterAddWithIndex.note.childrenNotes[0].title, "test")
            assertEquals(stateAfterAddWithIndex.note.childrenNotes[2].title, "test")
        }
    }
}