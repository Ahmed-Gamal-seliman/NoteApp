package com.example.note.feature_note.domain.use_case

import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteUseCase(
     val noteRepository: NoteRepository
) {

     suspend fun deleteNote(note: Note){
           noteRepository.deleteNote(note)
     }

     suspend fun addNote(note: Note){
          noteRepository.insertNote(note)
     }

     fun getNotes():Flow<List<Note>>?{
          return noteRepository.getNotes()
     }

     suspend fun getNote(title: String, content: String, color: Int):Note? {
          return noteRepository.getNote(title,content,color)
     }

}