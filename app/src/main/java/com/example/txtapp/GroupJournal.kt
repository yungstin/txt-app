package com.example.txtapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class GroupJournal: AppCompatActivity() {

    private lateinit var tempButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_journal)


        tempButton = findViewById(R.id.testButton)

        tempButton.setOnClickListener{
            val intent = Intent(this, AddGroup::class.java)
            startActivity(intent)
        }

    }

}