package com.ruma.notes.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruma.notes.data.database.entity.FolderEntity
import com.ruma.notes.databinding.ItemFolderBinding

class FolderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemFolderBinding.bind(view)

    fun render(folder: FolderEntity) {
        binding.tvName.setText(folder.name)
    }
}