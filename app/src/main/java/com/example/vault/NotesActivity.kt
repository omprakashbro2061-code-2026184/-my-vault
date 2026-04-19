package com.example.vault

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class NotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val prefs = getSharedPreferences("vault_notes", MODE_PRIVATE)
        val notesList = mutableListOf<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notesList)

        val listView = findViewById<ListView>(R.id.notesList)
        val editText = findViewById<EditText>(R.id.noteInput)
        val saveBtn = findViewById<Button>(R.id.saveNote)
        val deleteBtn = findViewById<Button>(R.id.deleteNote)

        listView.adapter = adapter

        fun loadNotes() {
            notesList.clear()
            val count = prefs.getInt("count", 0)
            for (i in 0 until count) {
                val note = prefs.getString("note_$i", "") ?: ""
                if (note.isNotEmpty()) notesList.add(note)
            }
            adapter.notifyDataSetChanged()
        }

        fun saveNotes() {
            val editor = prefs.edit()
            editor.putInt("count", notesList.size)
            notesList.forEachIndexed { i, note -> editor.putString("note_$i", note) }
            editor.apply()
        }

        loadNotes()

        saveBtn.setOnClickListener {
            val text = editText.text.toString().trim()
            if (text.isNotEmpty()) {
                notesList.add(text)
                saveNotes()
                adapter.notifyDataSetChanged()
                editText.text.clear()
            }
        }

        var selectedPosition = -1
        listView.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            editText.setText(notesList[position])
        }

        deleteBtn.setOnClickListener {
            if (selectedPosition >= 0 && selectedPosition < notesList.size) {
                notesList.removeAt(selectedPosition)
                saveNotes()
                adapter.notifyDataSetChanged()
                editText.text.clear()
                selectedPosition = -1
            }
        }
    }
}
