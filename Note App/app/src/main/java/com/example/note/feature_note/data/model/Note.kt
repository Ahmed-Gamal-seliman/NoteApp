package com.example.note.feature_note.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.note.R
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id:Int?=null,
    var title:String?,
    var content:String?,
    var color:Int?

):Parcelable
{
    companion object{
        val noteColors = listOf(R.color.Green, R.color.Yellow,R.color.BabyBlue, R.color.RedPink)
    }
}
