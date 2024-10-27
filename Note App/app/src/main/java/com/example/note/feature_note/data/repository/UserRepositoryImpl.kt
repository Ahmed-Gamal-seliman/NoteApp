//package com.example.note.feature_note.data.repository
//
//import android.app.Application
//import com.example.note.feature_note.data.data_source.NoteDao
//import com.example.note.feature_note.data.data_source.NoteDatabase
//import com.example.note.feature_note.data.data_source.UserDao
//import com.example.note.feature_note.data.data_source.UserDatabase
//import com.example.note.feature_note.data.model.Note
//import com.example.note.feature_note.data.model.User
//import com.example.note.feature_note.domain.repository.UserRepository
//import kotlinx.coroutines.flow.Flow
//
//class UserRepositoryImpl(
//    val application: Application
//): UserRepository {
//
//    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(application)
//    private val dao: UserDao? = userDatabase?.userDao()
//    override fun getAllUsers(): Flow<List<User>>? {
//        return dao?.getAllUsers()
//    }
//
//    override suspend fun insertUser(user: User) {
//         dao?.insertUser(user)
//    }
//
//    override suspend fun deleteUser(user: User) {
//        dao?.deleteUser(user)
//    }
//
//    override suspend fun getUser(name: String, password: String): User? {
//        return dao?.getUser(name,password)
//    }
//}