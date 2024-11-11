package com.example.note.feature_note.data.data_source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.note.feature_note.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
     fun getNotes(): Flow<List<Note>>


    @Query("SELECT * FROM note where id=:id")
    suspend fun getNoteById(id:Int): Note?

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Note WHERE title = :title AND content = :content AND color = :color")
    suspend fun getNote(title:String,content:String,color:Int):Note?

    @Update
    suspend fun updateNote(note:Note)

    @Query("SELECT * FROM Note WHERE userId= :userId")
    fun getNotesByUserId(userId:Int?): Flow<List<Note>>




}