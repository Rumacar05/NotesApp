package com.ruma.notes.ui.edit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ruma.notes.ui.home.MainActivity
import com.ruma.notes.R
import com.ruma.notes.databinding.ActivityNoteEditBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteEditBinding
    private var currentId: Long = 0
    private var parentId: Long? = null

    private val viewModel: NoteEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNoteEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentId = intent.getLongExtra(MainActivity.NOTE_ID, 0)
        parentId = intent.extras?.getLong(MainActivity.FOLDER_ID);

        initUI()
    }

    private fun initUI() {
        initListeners()

        if (currentId != 0L) {
            viewModel.loadNoteByID(currentId)
        }
        observeViewModel()
    }

    private fun initListeners() {
        binding.ivGoBack.setOnClickListener { finish() }
        binding.ivOptions.setOnClickListener { view -> showPopUpMenu(view) }
        binding.btnSave.setOnClickListener { saveNote() }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.note.collect { note ->
                if (note != null) {
                    binding.etTitle.setText(note.title)
                    binding.etContent.setText(note.content)
                }
            }
        }
    }

    private fun saveNote() {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()

        if (title.isNotEmpty() || content.isNotEmpty()) {
            viewModel.saveNote(title, content, parentId)
            Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Tienes que poner titulo o contenido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showPopUpMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_note_edit, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_delete -> {
                    showDeleteNoteDialog()
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

    private fun showDeleteNoteDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Estas seguro de borrar la nota ${binding.etTitle.text}?")
            .setNeutralButton("Borrar") { _, _ ->
                viewModel.deleteNote()
                finish()
                Toast.makeText(this, "Se ha borrado la nota correctamente", Toast.LENGTH_SHORT)
                    .show()
            }
            .setPositiveButton("Cancelar", null)

        dialog.show()
    }
}