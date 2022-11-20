package com.example.txtapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class GroupJournal: AppCompatActivity() {

    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_journal)
        wireWidgets()



        saveButton.setOnClickListener{
            val intent = Intent(this, AddGroup::class.java)
            startActivity(intent)
        }

    }

    private fun wireWidgets() {
        saveButton = findViewById(R.id.testButton)
    }

}