package com.ruma.notes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.ruma.notes.data.entity.Note
import com.ruma.notes.databinding.ActivityMainBinding
import com.ruma.notes.ui.NoteAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter

    private val notes = listOf(
        Note("Test", "Test"),
        Note("Test 2", "Test"),
        Note("Test 3", "Test"),
        Note("Test 4", "Test"),
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
        adapter = NoteAdapter(notes)
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.layoutManager = GridLayoutManager(this, 2)
        binding.rvNotes.adapter = adapter
    }
}