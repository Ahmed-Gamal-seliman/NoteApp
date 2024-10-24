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

class NoteAdapter( private val noteList:MutableList<Note>?):Adapter<NoteAdapter.NoteViewHolder>() {
   private lateinit var binding: NoteItemBinding

    var onClickIconDelete:IconDeleteListener?=null

     var onCardClick:CardClickListener?= null




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
            onClickIconDelete?.onIconDeleteClicked(noteList?.get(holder.adapterPosition),holder.adapterPosition)
        }

//        binding.root.setOnClickListener()
//        {
//            onCardClick?.onItemClicked(noteList?.get(holder.adapterPosition))
//        }
    }

    override fun getItemCount():Int = noteList?.size ?: 0


    fun addNote(note:Note){
       noteList?.add(note)
        notifyItemInserted(noteList?.size!!)
    }

    fun deleteNote(position:Int)
    {
        Log.e("show delete","before delete")
        noteList?.removeAt(position)
         notifyItemRemoved(position)
        Log.e("noteList Size",noteList?.size.toString())


    }
    fun noteListIsNotEmpty():Boolean{
        Log.e("size",noteList?.size.toString())
        return noteList?.size !=0
    }
    class NoteViewHolder(itemView: View):ViewHolder(itemView)


    interface IconDeleteListener{
        fun onIconDeleteClicked(note:Note?,position:Int):Unit
    }

    interface CardClickListener{
        fun onItemClicked(note:Note?)
    }
}