package com.ruma.notes.utils

import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ruma.notes.R

fun AppCompatActivity.showCreateFolderDialog(createFolder: (String) -> Unit) {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_folder, null)
    val etFolderName = dialogView.findViewById<EditText>(R.id.etFolderName)

    val dialog = AlertDialog.Builder(this)
        .setTitle(getString(R.string.create_folder))
        .setView(dialogView)
        .setNeutralButton(getString(R.string.create)) { _, _ ->
            val folderName = etFolderName.text.toString()

            if (folderName.isNotEmpty()) {
                createFolder(folderName)
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.folder_name_cannot_be_empty),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .setPositiveButton(getString(R.string.cancel), null)
        .create()
    dialog.show()
}