package com.example.note.feature_note.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.feature_note.data.model.User
import com.example.note.feature_note.data.repository.NoteRepositoryImpl
import com.example.note.feature_note.data.repository.UserRepositoryImpl
import com.example.note.feature_note.domain.use_case.NoteUseCase
import com.example.note.feature_note.domain.use_case.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AppViewModel(application: Application):ViewModel() {
    private val userUseCase: UserUseCase = UserUseCase(UserRepositoryImpl(application))

    fun getUser(name:String,password:String): User?
    {
        var user:User?
        runBlocking {
            user= userUseCase.getUser(name,password)
        }
        return user
    }

    fun insertUser(user:User)
    {
        runBlocking {
            userUseCase.insertUser(user)
        }
    }

    fun getAllUsers(): Flow<List<User>>?
    {
        return userUseCase.getAllUsers()

    }
}