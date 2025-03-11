package com.ruma.notes.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.ruma.notes.R
import com.ruma.notes.data.database.entity.FolderEntity
import com.ruma.notes.databinding.ActivityMainBinding
import com.ruma.notes.ui.edit.NoteEditActivity
import com.ruma.notes.ui.adapter.FolderAdapter
import com.ruma.notes.ui.adapter.NoteAdapter
import com.ruma.notes.ui.folder.FolderContentActivity
import com.ruma.notes.utils.showCreateFolderDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val NOTE_ID = "note_id"
        const val FOLDER_ID = "folder_id"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var folderAdapter: FolderAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initUI()
    }

    private fun initUI() {
        initUIState()
        initList()
        initListeners()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.loadNotesAndFolders()

                launch {
                    viewModel.folders.collect {
                        folderAdapter.updateList(it)
                    }
                }

                launch {
                    viewModel.notes.collect {
                        noteAdapter.updateList(it)
                    }
                }
            }
        }
    }

    private fun initList() {
        folderAdapter = FolderAdapter { navigateToFolder(it) }
        binding.rvFolders.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 4)
            adapter = folderAdapter
        }

        noteAdapter = NoteAdapter { navigateToNote(it) }
        binding.rvNotes.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = noteAdapter
        }
    }

    private fun initListeners() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, NoteEditActivity::class.java)
            startActivity(intent)
        }

        binding.ivOptions.setOnClickListener { view -> showPopUpMenu(view) }
    }

    private fun showPopUpMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_folder, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_create_folder -> {
                    showCreateFolderDialog { folderName -> createFolder(folderName) }
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

    private fun createFolder(folderName: String) {
        val folder = FolderEntity(name = folderName, parentFolderId = null)
        viewModel.insertFolder(folder)
        Toast.makeText(this, "Carpeta creada: $folderName", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToFolder(id: Long) {
        val intent = Intent(this, FolderContentActivity::class.java)
        intent.putExtra(FOLDER_ID, id)
        startActivity(intent)
    }

    private fun navigateToNote(id: Long) {
        val intent = Intent(this, NoteEditActivity::class.java)
        intent.putExtra(NOTE_ID, id)
        startActivity(intent)
    }
}