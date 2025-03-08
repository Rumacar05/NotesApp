package com.ruma.notes.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruma.notes.data.entity.Note
import com.ruma.notes.databinding.ItemNoteBinding

class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemNoteBinding.bind(view)

    fun bind(note: Note) {
        binding.tvTitle.text = note.title
        binding.tvContent.text = note.content
    }
}