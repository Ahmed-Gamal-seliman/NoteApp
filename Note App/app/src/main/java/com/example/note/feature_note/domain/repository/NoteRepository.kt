package com.example.note.feature_note.domain.repository

import androidx.lifecycle.LiveData
import com.example.note.feature_note.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

     fun getNotes(): Flow<List<Note>>?

    suspend fun getNoteById(id:Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun getNote(title:String,content:String,color:Int):Note?

    suspend fun updateNote(note:Note)


    fun getNotesByUserId(userId:Int?):Flow<List<Note>>?


}