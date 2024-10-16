package com.example.note.feature_note.presentation.add_edit_note.components

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.feature_note.data.data_source.NoteDao
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.data.repository.NoteRepositoryImpl
import com.example.note.feature_note.domain.use_case.NoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditNoteViewModel(application:Application):ViewModel() {

    private val noteUseCase: NoteUseCase = NoteUseCase(NoteRepositoryImpl(application))

     var note:Note? = null

     fun addNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteUseCase.noteRepository.insertNote(note)
        }
    }


}