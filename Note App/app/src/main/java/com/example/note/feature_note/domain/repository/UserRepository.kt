package com.example.note.feature_note.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {


    fun getAllUsers(): Flow<List<User>>?


    suspend fun insertUser(user: User)


    suspend fun deleteUser(user: User)


    suspend fun getUser(name:String,password:String): User?
}