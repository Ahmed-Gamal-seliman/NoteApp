package com.example.note.feature_note.presentation.add_edit_note.components

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.note.R
import com.example.note.databinding.ActivityAddEditNoteBinding
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.presentation.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditNoteActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddEditNoteBinding
    private lateinit var viewModel:AddEditNoteViewModel




    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= AddEditNoteViewModel(this.application)
        viewModel.note= Note(title=null,content=null,color=null)


        setPostColors()

        onSaveButtonClicked()

        onCircleColorsClicked()

        onBackArrowClicked()

        gti











    }

    private fun onBackArrowClicked() {
        binding.backBtn.setOnClickListener{
            finish()
        }
    }

    private fun onCircleColorsClicked() {
        binding.yellowCircle.setOnClickListener{
            changePostColor(R.color.Yellow)
            viewModel.note?.color=R.color.Yellow
        }

        binding.babyBlueCircle.setOnClickListener{
            changePostColor(R.color.BabyBlue)
            viewModel.note?.color=R.color.BabyBlue
        }
        binding.redPinkCircle.setOnClickListener{
            changePostColor(R.color.RedPink)
            viewModel.note?.color=R.color.RedPink
        }
        binding.greenCircle.setOnClickListener{
            changePostColor(R.color.Green)
            viewModel.note?.color=R.color.Green
        }
    }

    private fun changePostColor(color:Int) {
        binding.main.setBackgroundColor( ContextCompat.getColor(this, color))
      }

    private fun onSaveButtonClicked() {
        binding.saveBtn.setOnClickListener{

            /* Check title and body is not empty */

            val titleHandle:String? = handleTextFields(binding.TitleEt.text.toString())
            val bodyHandle:String? = handleTextFields(binding.noteBodyEt.text.toString())
            binding.apply {
                /* if any one is empty show an error required field !*/
                TitleEt.error = titleHandle
                noteBodyEt.error = bodyHandle
            }

            if((titleHandle==null) && (bodyHandle==null))
            {
                /* Save Note in Room Database through ViewModel*/
                /* return back to the Note Activity and appear the note on the screen */


                    viewModel.note?.title = binding.TitleEt.text.toString()
                    viewModel.note?.content = binding.noteBodyEt.text.toString()

                    if(viewModel.note?.color == null)
                    {
                        viewModel.note?.color = R.color.BabyBlue
                    }


                    viewModel.addNote(viewModel.note!!)

                    val intent = Intent()
                    intent.putExtra(Constants.NOTE_KEY, viewModel.note!!)
                    setResult(RESULT_OK, intent)
                    finish()


            }


        }
    }

    private fun handleTextFields(text:String): String? {
        if(text.isNotEmpty())
            return null
        return "required field!"
    }



    private fun isTitleAndBodyNotEmpty(): Boolean
    {
        return (binding.TitleEt.text.isNotEmpty() && binding.noteBodyEt.text.isNotEmpty())
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