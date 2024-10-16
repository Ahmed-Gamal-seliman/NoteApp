package com.example.note.feature_note.data.repository

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.note.feature_note.data.data_source.NoteDao
import com.example.note.feature_note.data.data_source.NoteDatabase
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    val application: Application
): NoteRepository {

    private var noteDatabase:NoteDatabase? = NoteDatabase.getDatabase(application)
    private val dao:NoteDao? = noteDatabase?.noteDao()
    override  fun getNotes(): Flow<List<Note>>? {
        return dao?.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao?.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao?.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao?.deleteNote(note)
    }
}