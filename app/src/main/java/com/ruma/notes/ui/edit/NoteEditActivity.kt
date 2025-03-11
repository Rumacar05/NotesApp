package com.ruma.notes.ui.edit

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.ruma.notes.R
import com.ruma.notes.databinding.ActivityNoteEditBinding
import com.ruma.notes.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteEditBinding
    private var currentId: Long = 0
    private var parentId: Long? = null
    private var isNoteDeleted = false

    private val viewModel: NoteEditViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())
    private val delayMillis = 2000L
    private val saveRunnable = Runnable {
        if(!isNoteDeleted) {
            viewModel.saveNote(
                binding.etTitle.text.toString(),
                binding.etContent.text.toString()
            )
        }
    }

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

    override fun onPause() {
        super.onPause()
        if (!isNoteDeleted) {
            viewModel.saveNote(
                binding.etTitle.text.toString(),
                binding.etContent.text.toString()
            )
        }
    }

    private fun initUI() {
        initUIListener()
        initListeners()

        if (currentId != 0L) {
            viewModel.loadNoteByID(currentId)
        }
    }

    private fun initListeners() {
        binding.ivGoBack.setOnClickListener { finish() }
        binding.ivOptions.setOnClickListener { view -> showPopUpMenu(view) }

        binding.etTitle.addTextChangedListener(afterTextChanged = { _ ->
            handler.removeCallbacks(saveRunnable)
            handler.postDelayed(saveRunnable, delayMillis)
        })

        binding.etContent.addTextChangedListener(afterTextChanged = { _ ->
            handler.removeCallbacks(saveRunnable)
            handler.postDelayed(saveRunnable, delayMillis)
        })
    }

    private fun initUIListener() {
        lifecycleScope.launch {
            viewModel.note.collect { note ->
                if (note != null) {
                    binding.etTitle.setText(note.title)
                    binding.etContent.setText(note.content)
                }
            }
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
                isNoteDeleted = true
                finish()
                Toast.makeText(this, "Se ha borrado la nota correctamente", Toast.LENGTH_SHORT)
                    .show()
            }
            .setPositiveButton("Cancelar", null)

        dialog.show()
    }
}