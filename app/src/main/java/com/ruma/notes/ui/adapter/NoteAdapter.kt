package com.ruma.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruma.notes.R
import com.ruma.notes.data.database.entity.NoteEntity

class NoteAdapter(
    private var noteList: List<NoteEntity> = emptyList(),
    private val onItemSelected: (Long) -> Unit
) :
    RecyclerView.Adapter<NoteViewHolder>() {

    fun updateList(list: List<NoteEntity>) {
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