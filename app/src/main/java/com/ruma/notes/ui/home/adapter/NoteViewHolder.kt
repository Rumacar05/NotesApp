package com.ruma.notes.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruma.notes.data.database.entity.NoteEntity
import com.ruma.notes.databinding.ItemNoteBinding

class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemNoteBinding.bind(view)

    fun render(note: NoteEntity, onItemSelected: (Long) -> Unit) {
        binding.tvTitle.text = note.title
        binding.tvContent.text = note.content

        itemView.setOnClickListener { onItemSelected(note.id) }
    }
}