package com.example.note.feature_note.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.note.feature_note.data.model.User


@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao():UserDao

    companion object {
        @Volatile
        var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class)
                {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext, UserDatabase::class.java,
                            "User_db"
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