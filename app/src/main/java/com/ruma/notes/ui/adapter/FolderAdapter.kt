package com.ruma.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruma.notes.R
import com.ruma.notes.domain.model.Folder

class FolderAdapter(
    private var folderList: List<Folder> = emptyList(),
    private val onItemSelected: (Long) -> Unit
) :
    RecyclerView.Adapter<FolderViewHolder>() {

    fun updateList(list: List<Folder>) {
        folderList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        return FolderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_folder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.render(folderList[position], onItemSelected)
    }

    override fun getItemCount() = folderList.size
}