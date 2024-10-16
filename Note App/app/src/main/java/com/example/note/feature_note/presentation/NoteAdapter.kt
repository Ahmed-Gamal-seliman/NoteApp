package com.example.note.feature_note.presentation


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.note.R
import com.example.note.databinding.NoteItemBinding
import com.example.note.feature_note.data.model.Note

class NoteAdapter(val noteList:MutableList<Note>?):Adapter<NoteAdapter.NoteViewHolder>() {
   private lateinit var binding: NoteItemBinding

    var onClickIconDelete:IconDeleteListener?=null




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        binding.noteTitle.text= noteList?.get(position)?.title.toString()
        binding.cardItem.setBackgroundColor( ContextCompat.getColor(binding.root.context,
            noteList?.get(position)?.color ?: R.color.Green
        ))

        binding.icDelete.setOnClickListener()
        {
            noteList?.let {
                noteList -> onClickIconDelete?.onIconDeleteClicked(noteList[position],position)
            }
        }
    }

    override fun getItemCount():Int = noteList?.size ?: 0


    fun addNote(note:Note){
       noteList?.add(note)
        notifyItemInserted(noteList?.size ?: 0)
    }

    fun deleteNote(note:Note,position:Int)
    {
        noteList?.remove(note)
        notifyItemRemoved(position)

    }
    class NoteViewHolder(itemView: View):ViewHolder(itemView)


    interface IconDeleteListener{
        fun onIconDeleteClicked(note:Note,position:Int):Unit
    }
}