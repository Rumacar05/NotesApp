package com.ruma.notes.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruma.notes.databinding.ItemFolderBinding
import com.ruma.notes.domain.model.Folder

class FolderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemFolderBinding.bind(view)

    fun render(folder: Folder, onItemSelected: (Long) -> Unit) {
        binding.tvName.text = folder.name
        binding.main.setOnClickListener { onItemSelected(folder.id) }
    }
}