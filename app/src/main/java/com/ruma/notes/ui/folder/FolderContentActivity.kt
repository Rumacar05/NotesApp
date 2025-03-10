package com.ruma.notes.ui.folder

import android.content.Intent
import android.os.Bundle
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
import com.ruma.notes.databinding.ActivityFolderContentBinding
import com.ruma.notes.ui.adapter.FolderAdapter
import com.ruma.notes.ui.adapter.NoteAdapter
import com.ruma.notes.ui.edit.NoteEditActivity
import com.ruma.notes.ui.home.MainActivity
import com.ruma.notes.ui.home.MainActivity.Companion.FOLDER_ID
import com.ruma.notes.ui.home.MainActivity.Companion.NOTE_ID
import com.ruma.notes.ui.home.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FolderContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFolderContentBinding
    private var folderId: Long = 0L

    private lateinit var noteAdapter: NoteAdapter
    private lateinit var folderAdapter: FolderAdapter

    private val viewModel: FolderContentViewModel by viewModels()

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
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.loadFolder(folderId)

                launch {
                    viewModel.currentFolder.collect {
                        binding.tvCategoryName.text = it?.name
                    }
                }

                launch {
                    viewModel.folder.collect {
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
        binding.ivGoBack.setOnClickListener { finish() }

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
                R.id.action_create_folder -> {
                    showCreateFolderDialog()
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

    private fun showCreateFolderDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_folder, null)
        val etFolderName = dialogView.findViewById<EditText>(R.id.etFolderName)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Crear carpeta")
            .setView(dialogView)
            .setNeutralButton("Crear") { dialog, which ->
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

    private fun createFolder(folderName: String) {
        val folder = FolderEntity(name = folderName, parentFolderId = folderId)
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