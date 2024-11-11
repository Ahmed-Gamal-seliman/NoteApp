package com.example.note.feature_note.domain.use_case

import androidx.lifecycle.LiveData
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.data.model.User
import com.example.note.feature_note.domain.repository.AppRepository
import com.example.note.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class AppUseCase(
    val appRepository: AppRepository
) {

    fun getNotes(): Flow<List<Note>>? {
        return appRepository.getNotes()
    }

    suspend fun getNoteById(id: Int): Note? {
        return appRepository.getNoteById(id)
    }

    suspend fun insertNote(note: Note) {
        appRepository.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        appRepository.deleteNote(note)
    }

    suspend fun getNote(title: String, content: String, color: Int): Note? {
        return appRepository.getNote(title,content,color)
    }

    suspend fun updateNote(note: Note) {
        appRepository.updateNote(note)
    }

       fun getNotesByUserId(userId: Int?): Flow<List<Note>>? {
        return appRepository.getNotesByUserId(userId)
    }

    fun getAllUsers(): Flow<List<User>>? {
        return appRepository.getAllUsers()
    }

    suspend fun insertUser(user: User) {
        appRepository.insertUser(user)
    }

    suspend fun deleteUser(user: User) {
        appRepository.deleteUser(user)
    }

    suspend fun getUser(name: String, password: String): User? {
        return  appRepository.getUser(name,password)
    }
}