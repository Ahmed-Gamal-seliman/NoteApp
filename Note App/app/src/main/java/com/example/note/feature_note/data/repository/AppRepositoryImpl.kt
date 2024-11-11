package com.example.note.feature_note.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.note.feature_note.data.data_source.AppDatabase
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.data.model.User
import com.example.note.feature_note.domain.repository.AppRepository
import com.example.note.feature_note.domain.repository.NoteRepository
import com.example.note.feature_note.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl(
    val application: Application
): AppRepository {

    private var appDatabase: AppDatabase? = AppDatabase.getDatabase(application)
    override fun getNotes(): Flow<List<Note>>? {
        return appDatabase?.noteDao()?.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return appDatabase?.noteDao()?.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        appDatabase?.noteDao()?.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        appDatabase?.noteDao()?.deleteNote(note)
    }

    override suspend fun getNote(title: String, content: String, color: Int): Note? {
        return appDatabase?.noteDao()?.getNote(title,content,color)
    }

    override suspend fun updateNote(note: Note) {
        appDatabase?.noteDao()?.updateNote(note)
    }

    override fun getNotesByUserId(userId: Int?): Flow<List<Note>>? {
        return appDatabase?.noteDao()?.getNotesByUserId(userId)
    }

    override fun getAllUsers(): Flow<List<User>>? {
        return appDatabase?.userDao()?.getAllUsers()
    }

    override suspend fun insertUser(user: User) {
        appDatabase?.userDao()?.insertUser(user)
    }

    override suspend fun deleteUser(user: User) {
        appDatabase?.userDao()?.deleteUser(user)
    }

    override suspend fun getUser(name: String, password: String): User? {
       return  appDatabase?.userDao()?.getUser(name,password)
    }
}