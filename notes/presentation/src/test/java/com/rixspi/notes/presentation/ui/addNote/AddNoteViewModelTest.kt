package com.rixspi.notes.presentation.ui.addNote

import com.rixspi.common.domain.interactors.CreateNote
import io.mockk.mockk
import org.junit.Before

class AddNoteViewModelTest {
    private lateinit var addNoteViewModel: AddNoteViewModel
    private val createNote: CreateNote = mockk(relaxed = true)

    @Before
    fun setup() {
        addNoteViewModel = AddNoteViewModel(
            AddNoteViewState(),
            createNote
        )
    }
}