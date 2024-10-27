package com.example.note.feature_note.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.note.R
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(foreignKeys = [ForeignKey(User::class,["id"],["userId"], onDelete = ForeignKey.CASCADE)])
data class Note(
    @PrimaryKey(autoGenerate = true) val id:Int?=null,
    var title:String?=null,
    var content:String?=null,
    var color:Int?=null,
    var userId:Int?=null


):Parcelable

