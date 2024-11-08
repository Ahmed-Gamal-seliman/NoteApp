package com.example.note.feature_note.presentation.notes.components

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
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
import com.example.note.feature_note.data.model.User
import com.example.note.feature_note.presentation.AppViewModel
import com.example.note.feature_note.presentation.Constants
import com.example.note.feature_note.presentation.NoteAdapter
import com.example.note.feature_note.presentation.Search.SearchActivity
import com.example.note.feature_note.presentation.add_edit_note.components.AddEditNoteActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class NotesActivity : AppCompatActivity() {

    lateinit var binding:ActivityNotesBinding
    private lateinit var viewModel: AppViewModel

    private lateinit var noteAdapter:NoteAdapter

    private val activityLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback {result->

                    val intent = result.data
                    val note = intent?.getParcelableExtra<Note>(Constants.NOTE_KEY)
                    viewModel.noteMainList?.clear()
                runBlocking {
                    viewModel.noteMainList =
                        viewModel.getNotesByUserId(AppViewModel.user?.id)?.first()?.toMutableList()


                }
                    noteAdapter.clearAndAddNoteList(viewModel.noteMainList)
                    viewModel.noteMainList?.forEach {
                        note->
                        Log.e("noteM",note.title ?: "No title")
                    }
                    Log.e("noteM",viewModel.noteMainList?.size.toString())
                   // noteAdapter.notifyItemRangeChanged(0,viewModel.noteMainList?.size ?: 0)
//                    if(note!=null) {
//                        Log.e("noteAdapter",note.title!!)
//                        noteAdapter.addNote(note)
//                        viewModel.noteMainList?.forEach {
//                            note->
//                            Log.e("note title",note.title ?: "no title")
//
//                        }
//
//                    }

                    checkIfNotesIsEmpty()




                }
            )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initNoteAdapter()
        intiNotesRecyclerView()

        welcomeUser()
        showAllNotesWhenUserLogin()

        checkIfNotesIsEmpty()
        onClickFloatingActionButton()
        onClickDeleteIcon()

        onCardItemClicked()

        onThreeDotsClicked()

        onSearchIconClicked()




    }

    private fun initNoteAdapter() {
        noteAdapter= NoteAdapter(viewModel.noteMainList)
    }

    private fun onSearchIconClicked() {
        binding.searchBtn.setOnClickListener {
            val intent = Intent(this@NotesActivity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showSearchBar() {

    }

    private fun showAllNotesWhenUserLogin() {

    }

    private fun welcomeUser() {
        val user:User?=intent.getParcelableExtra<User>(Constants.USER_KEY)
        binding.UserNameTv.text = "${user?.name} Notes" ?: "No name"
        binding.userWelcomeTextView.text= "Hello ${user?.name}!\nCreate your first note!"
    }

    private fun onThreeDotsClicked() {

    }

    private fun onCardItemClicked() {
//        viewModel.noteAdapter.onCardClick = object:NoteAdapter.CardClickListener{
//            override fun onItemClicked(note:Note?) {
//                val intent= Intent(this@NotesActivity,AddEditNoteActivity::class.java)
//                intent.putExtra(Constants.NOTE_KEY,note)
//                startActivity(intent)
//
//
//
//            }
//        }
    }

    private fun onClickDeleteIcon() {
        noteAdapter.onClickIconDelete= object :NoteAdapter.IconDeleteListener{

            override fun onIconDeleteClicked(note:Note?, position:Int) {
                /* Remove from Note List and from Room Database*/
                var realnote:Note?=null
                runBlocking {
                    realnote = viewModel.getNote(
                        title = note?.title!!,
                        content = note.content!!,
                        color = note.color!!
                    )
                }

            if (realnote != null) {
                runBlocking {
                    viewModel.deleteNote(realnote!!, position)
                }
                //noteAdapter.deleteNote(position)
                viewModel.noteMainList?.clear()
                runBlocking {
                    viewModel.noteMainList =
                        viewModel.getNotesByUserId(AppViewModel.user?.id)?.first()?.toMutableList()

                }
                Log.e("indelete",viewModel.noteMainList?.size.toString())
                noteAdapter.clearAndAddNoteList(viewModel.noteMainList)
                viewModel.noteMainList?.forEach {
                    note->
                    Log.e("notedel : ViewModel",note.title ?: "No title")
                }

            }


                checkIfNotesIsEmpty()


            }
        }


    }

    private fun checkIfNotesIsEmpty() {
        /* Check there is a list or not */
        if (noteAdapter.noteListIsNotEmpty()) {
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
        binding.rvNotes.adapter = noteAdapter
        binding.rvNotes.layoutManager= LinearLayoutManager(this)
    }

    private fun initViewModel() {
        viewModel = AppViewModel(this.application)
    }

    private fun onClickFloatingActionButton() {
        binding.fabAdd.setOnClickListener {
           val intent:Intent= Intent(this@NotesActivity,AddEditNoteActivity::class.java)
            activityLauncher.launch(intent)
        }
    }


}