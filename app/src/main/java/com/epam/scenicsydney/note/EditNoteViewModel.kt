package com.epam.scenicsydney.note

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.epam.scenicsydney.model.Note

class EditNoteViewModel : ViewModel() {

    private val selectedNote: MutableLiveData<Note> = MutableLiveData()

    fun getSelectedNote(): LiveData<Note> = selectedNote

    fun setSelectedNote(note: Note) {
        selectedNote.value = note
    }

    fun setText(text: String) {
        selectedNote.value?.let {
            it.text = text
            selectedNote.value = it
        }
    }
}