package com.example.note.feature_note.presentation.notes.components

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.R
import com.example.note.databinding.ActivityNotesBinding
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.presentation.Constants
import com.example.note.feature_note.presentation.NoteAdapter
import com.example.note.feature_note.presentation.add_edit_note.components.AddEditNoteActivity
import com.example.note.feature_note.presentation.add_edit_note.components.AddEditNoteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class NotesActivity : AppCompatActivity() {

    lateinit var binding:ActivityNotesBinding
    private lateinit var viewModel: NotesViewModel

    private val activityLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback {result->

                    val intent = result.data
                    val note = intent?.getParcelableExtra<Note>(Constants.NOTE_KEY)
                runBlocking {
                    viewModel.noteMainList= viewModel.getNotes()?.first()?.toMutableList()

                    if(note!=null)
                        viewModel.noteAdapter.addNote(note)
                }

                    checkIfNotesIsEmpty()




                }
            )


    private val editActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {result->

                val intent = result.data
                val note = intent?.getParcelableExtra<Note>(Constants.NOTE_KEY)
                runBlocking {
                    viewModel.noteMainList= viewModel.getNotes()?.first()?.toMutableList()

                    if(note!=null)
                        viewModel.noteAdapter.addNote(note)
                }

                checkIfNotesIsEmpty()




            }
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        intiNotesRecyclerView()
        checkIfNotesIsEmpty()
        onClickFloatingActionButton()
        onClickDeleteIcon()

        onCardItemClicked()




    }

    private fun onCardItemClicked() {
        viewModel.noteAdapter.onCardClick = object:NoteAdapter.CardClickListener{
            override fun onItemClicked(note:Note?) {
                val intent= Intent(this@NotesActivity,AddEditNoteActivity::class.java)
                intent.putExtra(Constants.NOTE_KEY,note)
                startActivity(intent)



            }
        }
    }

    private fun onClickDeleteIcon() {
        viewModel.noteAdapter.onClickIconDelete= object :NoteAdapter.IconDeleteListener{

            override fun onIconDeleteClicked(note:Note?, position:Int) {
                /* Remove from Note List and from Room Database*/
              val realnote = viewModel.getNote(title=note?.title!!, content = note.content!! ,color = note.color!!)




                    if (realnote != null) {
                        viewModel.deleteNote(realnote, position)
                        viewModel.noteAdapter.deleteNote(position)

                    }


                checkIfNotesIsEmpty()


            }
        }


    }

    private fun checkIfNotesIsEmpty() {
        /* Check there is a list or not */
        if (viewModel.noteAdapter.noteListIsNotEmpty()) {
            showNoteRecyclerView()
            Log.e("show","note recycler")
        } else {
            showNoteImage()
            Log.e("show","note image")
        }
    }

    private fun showNoteImage() {
        binding.rvNotes.isVisible = false
        binding.homeNoteImg.isVisible = true
        binding.userWelcomeTextView.isVisible = true
    }

    private fun showNoteRecyclerView() {
        binding.homeNoteImg.isVisible = false
        binding.userWelcomeTextView.isVisible = false
        binding.rvNotes.isVisible = true
    }

    private fun intiNotesRecyclerView() {
        binding.rvNotes.adapter = viewModel.noteAdapter
        binding.rvNotes.layoutManager= LinearLayoutManager(this)
    }

    private fun initViewModel() {
        viewModel = NotesViewModel(this.application)
    }

    private fun onClickFloatingActionButton() {
        binding.fabAdd.setOnClickListener {
           val intent:Intent= Intent(this@NotesActivity,AddEditNoteActivity::class.java)
            activityLauncher.launch(intent)
        }
    }
}