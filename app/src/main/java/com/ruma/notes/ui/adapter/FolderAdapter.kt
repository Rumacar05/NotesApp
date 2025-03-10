package com.ruma.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruma.notes.R
import com.ruma.notes.data.database.entity.FolderEntity

class FolderAdapter(private var folderList: List<FolderEntity> = emptyList()) :
    RecyclerView.Adapter<FolderViewHolder>() {

    fun updateList(list: List<FolderEntity>) {
        folderList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        return FolderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_folder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.render(folderList[position])
    }

    override fun getItemCount() = folderList.size
}