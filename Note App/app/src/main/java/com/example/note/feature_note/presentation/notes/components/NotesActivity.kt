package com.example.note.feature_note.presentation.notes.components

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note.databinding.ActivityNotesBinding
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.data.model.User
import com.example.note.feature_note.presentation.AppViewModel
import com.example.note.feature_note.presentation.Constants
import com.example.note.feature_note.presentation.NoteAdapter
import com.example.note.feature_note.presentation.Search.SearchActivity
import com.example.note.feature_note.presentation.add_edit_note.components.AddEditNoteActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class NotesActivity : AppCompatActivity() {

    lateinit var binding:ActivityNotesBinding
    private lateinit var viewModel: AppViewModel

    private lateinit var noteAdapter:NoteAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val activityLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback {result->

                val note= result.data?.getParcelableExtra(Constants.NOTE_KEY,Note::class.java)

                    runBlocking {
                        if (note != null)
                            viewModel.addNote(note)

                        Log.e("activity", "ok")
                        viewModel.refreshData()
                    }





//                    checkIfNotesIsEmpty()




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

        checkIfNotesIsEmpty()


    }

    private fun initNoteAdapter() {
        noteAdapter= NoteAdapter(emptyList<Note>().toMutableList())
   lifecycleScope.launch(Dispatchers.IO) {

       val list:MutableList<Note> = mutableListOf()
        viewModel.noteMainList?.collect()
        {
            list.clear()
            list.addAll(it)
            Log.e("in collect",it.toString())
            withContext(Dispatchers.Main)
            {
                Log.e("context collect","yes")
                noteAdapter.setList(list)
                checkIfNotesIsEmpty()
            }
        }


    }

//        viewModel.noteMainList.observe(this)
//        {
////            Log.e("notes",it.toString())
//            Toast.makeText(this@NotesActivity,"here",Toast.LENGTH_LONG).show()
//            Log.e("list",it?.toString() ?: "No items")
//            if(it?.size != 0) {
////                Log.e("it size",it.size.toString())
//                noteAdapter.setList(it?.toList() ?: emptyList<Note>())
//            }
//            else{
//                Log.e("it size","0")
//            }
//            checkIfNotesIsEmpty()
//        }


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


                /* Delete from database*/
                viewModel.deleteNote(note!!)

                //noteAdapter.deleteNote(position)
                noteAdapter.notifyItemRemoved(position)

                viewModel.refreshData()

                /* Empty the list */
//                viewModel.noteMainList= emptyList<Note>().toMutableList()
//                noteAdapter.noteList =  viewModel.noteMainList
                /* get all notes for the user after deletion*/
//                runBlocking {
//
//                        noteAdapter.setList(viewModel.getNotesByUserId(AppViewModel.user?.id)?.first() ?: emptyList())
//
//
//                }

//                if( viewModel.noteMainList?.size != 0)
//                    viewModel.noteMainList?.forEach {
//                            note->
//                        Log.e("note in delete after",note.title ?: "No title")
//                    }
//                else
//                    Log.e("note in delete after","size is zero")

//                noteAdapter.noteList?.addAll(viewModel.noteMainList!!)
//                Log.e("noteList",noteAdapter.noteList.toString())

//                noteAdapter.clearAndAddNoteList(viewModel.noteMainList)
//                    noteAdapter.notifyItemRemoved(position)

//                checkIfNotesIsEmpty()

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