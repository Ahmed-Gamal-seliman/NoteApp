package com.example.note.feature_note.presentation.notes.components

import android.app.Application
import android.util.Log
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
import kotlinx.coroutines.withContext

class NotesViewModel(application:Application) :ViewModel() {


    private val noteUseCase: NoteUseCase = NoteUseCase(NoteRepositoryImpl(application))

     var noteAdapter:NoteAdapter
    var noteMainList: MutableList<Note>? = null


    init {
        runBlocking {
            noteMainList =getNotes()?.first()?.toMutableList()
            }

        noteAdapter = NoteAdapter(noteMainList)
    }

     fun getNotes(): Flow<List<Note>>? {
         return noteUseCase.getNotes()
     }


    fun deleteNote(note:Note,position:Int)
    {

        viewModelScope.launch(Dispatchers.Main) {
//            withContext(Dispatchers.IO) {
                 noteUseCase.deleteNote(note)
//            }

        }

    }

    fun getNote(title: String, content: String, color: Int):Note? {
        var note:Note? = null
       runBlocking {
            note = noteUseCase.getNote(title, content, color)

        }
        return note

    }





}