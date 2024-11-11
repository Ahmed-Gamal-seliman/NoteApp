package com.example.note.feature_note.presentation.Search

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.note.R
import com.example.note.databinding.ActivitySearchBinding
import com.example.note.feature_note.data.model.Note
import com.example.note.feature_note.presentation.AppViewModel
import com.example.note.feature_note.presentation.NoteAdapter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SearchActivity : AppCompatActivity() {

private lateinit var binding:ActivitySearchBinding
private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AppViewModel(this.application)
        

        showNotesForUser(viewModel.noteMainList?.value?.toMutableList())
        onSearchIconClicked()



    }

    private fun onSearchIconClicked() {
        binding.searchView.setOnClickListener{
            binding.searchView.apply {
                queryHint= "search title"

            }

        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText?.isEmpty() == true)
                {
                    showNotesForUser(viewModel.noteMainList?.value?.toMutableList())
                    return false
                }
                val filteredList = filterNotesList(newText)

                showNotesForUser(filteredList)

                return false
            }
        })
    }

    private fun filterNotesList(newText:String?):MutableList<Note>? {
        val filteredList = mutableListOf<Note>()
        viewModel.noteMainList?.value?.forEach {note->
            if(note.title?.contains(newText.toString()) == true)
            {
                filteredList.add(note)
            }

        }
        return filteredList
    }

    private fun showNotesForUser(notes:MutableList<Note>?) {
        binding.rvSearchList.apply{
            adapter = NoteAdapter(notes)
            if(viewModel.noteMainList?.value?.isEmpty() == true)
            {
                showListisEmpty()
            }
        }

    }

    private fun showListisEmpty() {
        binding.errorEmptyListTv.text= getString(R.string.list_is_empty)
    }
}