package com.ruma.notes.ui.edit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruma.notes.R
import com.ruma.notes.data.database.entity.Note

class NoteAdapter(private var noteList: List<Note> = emptyList(), private val onItemSelected: (Note)-> Unit) :
    RecyclerView.Adapter<NoteViewHolder>() {

    fun updateList(list: List<Note>) {
        noteList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: NoteViewHolder, position: Int) {
        viewHolder.render(noteList[position], onItemSelected)
    }

    override fun getItemCount(): Int = noteList.size
}