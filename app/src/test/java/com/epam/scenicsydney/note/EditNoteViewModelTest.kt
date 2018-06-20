package com.epam.scenicsydney.note

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.epam.scenicsydney.any
import com.epam.scenicsydney.model.Note
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EditNoteViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: EditNoteViewModel

    // observed note
    private lateinit var selectedNote: Note

    private val observer = Observer<Note> {
        selectedNote = it ?: any()
    }

    @Before
    fun setUp() {
        viewModel = EditNoteViewModel()
        viewModel.getSelectedNote().observeForever(observer)
    }

    @After
    fun tearDown() {
        viewModel.getSelectedNote().removeObserver(observer)
    }

    @Test
    fun setSelectedNote_isSet() {
        val noteText = "Note 1"
        val note = Note(1, noteText, 1)
        viewModel.setSelectedNote(note)
        assertEquals("Note text", noteText, selectedNote.text)
    }

    @Test
    fun setNoteText_isSelectedNoteChanged() {
        val note = Note(1, "Note 1", 1)
        viewModel.setSelectedNote(note)
        val newNoteText = "Note 1 changed"
        viewModel.setText(newNoteText)
        assertEquals("Note text", newNoteText, selectedNote.text)
    }

    @Test
    fun setTextWhenSelectedNoteIsNull_isNoError() {
        assertFalse("Selected note is not initialized", ::selectedNote.isInitialized)
        viewModel.setText("Note 1")
        assertFalse("Selected note is not initialized", ::selectedNote.isInitialized)
    }
}