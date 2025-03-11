package com.ruma.notes.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruma.notes.databinding.ItemNoteBinding
import com.ruma.notes.domain.model.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemNoteBinding.bind(view)

    fun render(note: Note, onItemSelected: (Long) -> Unit) {
        if (note.title.isNotEmpty()) {
            binding.tvTitle.text = note.title
        } else {
            binding.tvTitle.text = "Nota ${formatTimestamp(note.timestamp)}"
        }
        binding.tvContent.text = note.content

        binding.main.setOnClickListener { onItemSelected(note.id) }
    }

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }
}