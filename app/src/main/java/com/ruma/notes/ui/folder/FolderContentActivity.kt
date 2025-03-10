package com.ruma.notes.ui.folder

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ruma.notes.R
import com.ruma.notes.databinding.ActivityFolderContentBinding
import com.ruma.notes.ui.adapter.FolderAdapter
import com.ruma.notes.ui.adapter.NoteAdapter
import com.ruma.notes.ui.edit.NoteEditActivity
import com.ruma.notes.ui.home.MainActivity
import com.ruma.notes.ui.home.MainActivity.Companion.FOLDER_ID
import com.ruma.notes.ui.home.MainActivity.Companion.NOTE_ID
import com.ruma.notes.ui.home.MainViewModel
import kotlinx.coroutines.launch

class FolderContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFolderContentBinding
    private var folderId: Long = 0L

    private val viewModel: FolderContentViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var folderAdapter: FolderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFolderContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        folderId = intent.getLongExtra(MainActivity.FOLDER_ID, 0L)
        if (folderId == 0L) {
            finish()
            return
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
            viewModel.loadFolder(folderId)
        }
    }

    private fun initList() {
        folderAdapter = FolderAdapter { navigateToFolder(it) }
        binding.rvFolders.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = folderAdapter
        }

        noteAdapter = NoteAdapter { navigateToNote(it)}
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

        popupMenu.menu.findItem(R.id.action_delete_folder).isVisible = true

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                else -> false
            }
        }

        popupMenu.show()
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