package com.ruma.notes.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruma.notes.data.database.entity.FolderEntity
import com.ruma.notes.databinding.ItemFolderBinding

class FolderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemFolderBinding.bind(view)

    fun render(folder: FolderEntity, onItemSelected: (Long) -> Unit) {
        binding.tvName.text = folder.name
        binding.main.setOnClickListener { onItemSelected(folder.id) }
    }
}