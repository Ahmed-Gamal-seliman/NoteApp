package com.example.note.feature_note.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.data.model.User


@Database(entities = [User::class, Note::class],
    version=1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao():UserDao
    abstract fun noteDao():NoteDao


    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class)
                {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext, AppDatabase::class.java,
                            "App_db"
                        )
                            .allowMainThreadQueries()
                            .build()


                    }
                }
            }
            return INSTANCE;
        }
    }
}