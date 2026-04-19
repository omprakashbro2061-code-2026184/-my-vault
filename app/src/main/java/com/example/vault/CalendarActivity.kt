package com.example.vault

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val prefs = getSharedPreferences("vault_calendar", MODE_PRIVATE)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val eventInput = findViewById<EditText>(R.id.eventInput)
        val saveBtn = findViewById<Button>(R.id.saveEvent)
        val eventText = findViewById<TextView>(R.id.eventDisplay)

        var selectedDate = System.currentTimeMillis().toString()

        calendarView.setOnDateChangeListener { _, year, month, day ->
            selectedDate = "$year-${month+1}-$day"
            val event = prefs.getString(selectedDate, "No events")
            eventText.text = event
        }

        saveBtn.setOnClickListener {
            val event = eventInput.text.toString().trim()
            if (event.isNotEmpty()) {
                prefs.edit().putString(selectedDate, event).apply()
                eventText.text = event
                eventInput.text.clear()
                Toast.makeText(this, "Event saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
