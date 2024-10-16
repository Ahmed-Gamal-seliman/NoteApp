package com.example.note.feature_note.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.note.feature_note.data.model.Note


@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao():NoteDao

    companion object {
        @Volatile
        var INSTANCE: NoteDatabase? = null
        fun getDatabase(context: Context): NoteDatabase? {
            if (INSTANCE == null) {
                synchronized(NoteDatabase::class)
                {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext, NoteDatabase::class.java,
                            "Task_db"
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