package com.example.note.feature_note.presentation.notes.components

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.data.repository.NoteRepositoryImpl
import com.example.note.feature_note.domain.use_case.NoteUseCase
import com.example.note.feature_note.presentation.NoteAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NotesViewModel(application:Application) :ViewModel() {


    private val noteUseCase: NoteUseCase = NoteUseCase(NoteRepositoryImpl(application))

     var noteAdapter:NoteAdapter
    var noteMainList: List<Note>? = null


    init {
        runBlocking {
            noteMainList =getNotes()?.first()
            }

        noteAdapter = NoteAdapter(noteMainList?.toMutableList())
    }

     fun getNotes(): Flow<List<Note>>? {
         return noteUseCase.noteRepository.getNotes()
     }


    fun deleteNote(note:Note)
    {

        viewModelScope.launch(Dispatchers.IO) {
            noteUseCase.noteRepository.deleteNote(note)
        }

    }


}