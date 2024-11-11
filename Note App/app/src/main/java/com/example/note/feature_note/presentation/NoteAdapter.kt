package com.example.note.feature_note.presentation


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.note.R
import com.example.note.databinding.NoteItemBinding
import com.example.note.feature_note.data.model.Note

class NoteAdapter( private var noteList:MutableList<Note>?):Adapter<NoteAdapter.NoteViewHolder>() {
   private lateinit var binding: NoteItemBinding

    var onClickIconDelete:IconDeleteListener?=null

     var onCardClick:CardClickListener?= null



    @SuppressLint("NotifyDataSetChanged")
    fun setList(notes: List<Note>)
    {
        Log.e("notes size",notes.size.toString())
        if(noteList == null)
            Log.e("noteList","null")
        else{
            Log.e("noteList","not null")
            noteList?.clear()
            noteList?.addAll(notes)
            Log.e("noteList",noteList.toString())
        }


        notifyDataSetChanged()
        Log.e("noteList in setList",noteList?.size.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        Log.e("titleBind",noteList?.get(holder.adapterPosition)?.title.toString())
        binding.noteTitle.text= noteList?.get(holder.adapterPosition)?.title.toString()
        binding.cardItem.setCardBackgroundColor(ContextCompat.getColor(binding.root.context,
            noteList?.get(holder.adapterPosition)?.color ?: R.color.Green
        ))
//        binding.cardItem.setBackgroundColor( ContextCompat.getColor(binding.root.context,
//            noteList?.get(position)?.color ?: R.color.Green
//        ))




        binding.icDelete.setOnClickListener()
        {
            Log.e("note in bind",holder.adapterPosition.toString())
            onClickIconDelete?.onIconDeleteClicked(noteList?.get(holder.adapterPosition),holder.adapterPosition)
        }

//        binding.root.setOnClickListener()
//        {
//            onCardClick?.onItemClicked(noteList?.get(holder.adapterPosition))
//        }
    }


   

    override fun getItemCount():Int {
        Log.e("size",noteList?.size.toString())
        return noteList?.size ?: 0
    }


    fun clearAndAddNoteList(notes:MutableList<Note>?)
    {
        noteList?.clear()
        notes?.forEachIndexed { index,note->
            noteList?.add(index,note)
            Log.e("notedel : adapter",note.title ?: "No title")
        }
        noteList?.forEachIndexed { index, note->
            Log.e("note in func","${note.title}: ${index}")
        }
        notifyDataSetChanged()

    }
    fun addNote(note:Note){
       noteList?.add(note)
        notifyItemInserted(noteList?.size!!)
        noteList?.forEach { note->
            Log.e("note item",note.title ?: "no title")
        }
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