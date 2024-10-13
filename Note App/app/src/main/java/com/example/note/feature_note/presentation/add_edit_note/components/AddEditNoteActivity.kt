package com.example.note.feature_note.presentation.add_edit_note.components

import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.note.R
import com.example.note.databinding.ActivityAddEditNoteBinding

class AddEditNoteActivity : AppCompatActivity() {


    lateinit var binding: ActivityAddEditNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPostColors()




    }

    private fun setPostColors() {

        binding.apply {

            changeCircleColor(babyBlueCircle,R.color.BabyBlue)
            changeCircleColor(redPinkCircle,R.color.RedPink)
            changeCircleColor(yellowCircle,R.color.Yellow)
            changeCircleColor(greenCircle,R.color.Green)
        }
    }

    private fun changeCircleColor(imageButton:ImageButton,color: Int) {
        val wrappedDrawable= DrawableCompat.wrap(imageButton.drawable)
        DrawableCompat.setTint(
            wrappedDrawable,
            ContextCompat.getColor(imageButton.context,color)

        )
        imageButton.setImageDrawable(wrappedDrawable)
    }
}