package com.example.txtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btndailyjournal: Button
    private lateinit var btnAddGroup: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btndailyjournal = findViewById(R.id.dailyjournal)

        btndailyjournal.setOnClickListener {
            val intent = Intent(this, DailyJournal::class.java)
            startActivity(intent)
        }

        btnAddGroup = findViewById(R.id.addGroup)

        btnAddGroup.setOnClickListener {
            val intent = Intent(this, AddGroup::class.java)
            startActivity(intent)
        }

    }
}