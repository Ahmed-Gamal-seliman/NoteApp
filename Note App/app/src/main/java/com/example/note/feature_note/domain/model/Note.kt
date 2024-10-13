package com.example.note.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.note.R


@Entity
data class Note(
    val title:String,
    val content:String,
    val timestamp:Long,
    val color:Int,
    @PrimaryKey
    val id:Int?= null
)
{
    companion object{
        val noteColors = listOf(R.color.Green, R.color.Yellow,R.color.BabyBlue, R.color.RedPink)
    }
}
