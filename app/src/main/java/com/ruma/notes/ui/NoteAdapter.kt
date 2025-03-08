package com.ruma.notes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruma.notes.R
import com.ruma.notes.data.entity.Note

class NoteAdapter(private var noteList: List<Note> = emptyList(), private val onItemSelected: (Long)-> Unit) :
    RecyclerView.Adapter<NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: NoteViewHolder, position: Int) {
        viewHolder.bind(noteList[position], onItemSelected)
    }

    override fun getItemCount(): Int = noteList.size
}