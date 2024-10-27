package com.example.note.feature_note.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id:Int?=null,
    var name:String?=null,
    var email:String?=null,
    var password:String?=null


) : Parcelable

