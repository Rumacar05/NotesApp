package com.ruma.notes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.ruma.notes.data.entity.Note
import com.ruma.notes.databinding.ActivityMainBinding
import com.ruma.notes.ui.NoteAdapter
import com.ruma.notes.ui.NoteEditActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter

    companion object {
        const val NOTE_ID = "note_id"
    }

    private val notes = listOf(
        Note(1, "Test", "Test"),
        Note(2, "Test 2", "Test"),
        Note(3, "Test 3", "Test"),
        Note(4, "Test 4", "Test"),
    )

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
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, NoteEditActivity::class.java)
            startActivity(intent)
        }

        adapter = NoteAdapter(notes) {id -> navigateToNote(id)}
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.layoutManager = GridLayoutManager(this, 2)
        binding.rvNotes.adapter = adapter
    }

    private fun navigateToNote(id: Long) {
        val intent = Intent(this, NoteEditActivity::class.java)
        intent.putExtra(NOTE_ID, id)
        startActivity(intent)
    }
}