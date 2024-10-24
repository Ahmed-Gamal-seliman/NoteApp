package com.example.note.feature_note.domain.use_case

import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.data.model.User
import com.example.note.feature_note.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserUseCase(
    val userRepository: UserRepository
){
    suspend fun getUser(name:String,password:String): User?
    {
        return userRepository.getUser(name,password)
    }

    suspend fun insertUser(user: User)
    {
        userRepository.insertUser(user)
    }

    fun getAllUsers(): Flow<List<User>>?
    {
        return userRepository.getAllUsers()

    }

}