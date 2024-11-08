package com.example.note.feature_note.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.data.model.User
import com.example.note.feature_note.data.repository.AppRepositoryImpl
import com.example.note.feature_note.domain.use_case.AppUseCase
import com.example.note.feature_note.domain.use_case.NoteUseCase
import com.example.note.feature_note.domain.use_case.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AppViewModel(application: Application):ViewModel() {
    private val appUseCase: AppUseCase = AppUseCase(AppRepositoryImpl(application))

    companion object {
         var user: User? = null
    }

    var note: Note? = null


    var noteMainList: MutableList<Note>? = null

    init {
        runBlocking {
            noteMainList =getNotesByUserId(AppViewModel.user?.id)?.first()?.toMutableList()
        }

//        noteAdapter = NoteAdapter(noteMainList)
    }
    fun addNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            appUseCase.insertNote(note)
        }
    }
    fun getUser(email:String,password:String): User?
    {
        runBlocking {
            user= appUseCase.getUser(email,password)
        }
        return user
    }



    fun insertUser(user:User)
    {
        runBlocking {
            appUseCase.insertUser(user)
        }
    }

    fun getAllUsers(): Flow<List<User>>?
    {
        return appUseCase.getAllUsers()

    }

    fun getNotesByUserId(id:Int?): Flow<List<Note>>? {
        return appUseCase.getNotesByUserId(id)
    }


    fun deleteNote(note:Note,position:Int)
    {

//        viewModelScope.launch(Dispatchers.Main) {
//            withContext(Dispatchers.IO) {
        runBlocking {
            appUseCase.deleteNote(note)
        }
//            }

//        }

    }

    fun getNote(title: String, content: String, color: Int):Note? {
        var note:Note? = null
        runBlocking {
            note = appUseCase.getNote(title, content, color)

        }
        return note

    }
}