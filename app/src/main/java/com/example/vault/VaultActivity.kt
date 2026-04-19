package com.example.vault

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class VaultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)

        findViewById<LinearLayout>(R.id.btnWeather).setOnClickListener {
            startActivity(Intent(this, WeatherActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.btnAlarm).setOnClickListener {
            startActivity(Intent(this, AlarmActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.btnCalendar).setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.btnNotes).setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java))
        }
    }
}
