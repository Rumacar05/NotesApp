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
        .setTitle("Crear carpeta")
        .setView(dialogView)
        .setNeutralButton("Crear") { _, _ ->
            val folderName = etFolderName.text.toString()

            if (folderName.isNotEmpty()) {
                createFolder(folderName)
            } else {
                Toast.makeText(
                    this,
                    "El nombre de la carpeta no puede estar vacío",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .setPositiveButton("Cancelar", null)
        .create()
    dialog.show()
}