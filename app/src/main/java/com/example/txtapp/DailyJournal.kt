package com.example.txtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DailyJournal : AppCompatActivity() {
    private lateinit var dailyjournaledt: EditText
    private lateinit var returnbtn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dailyjournal)

        dailyjournaledt = findViewById(R.id.journalentry)
        returnbtn = findViewById(R.id.saveButton)

        val dailyentry = dailyjournaledt.text.toString()


        returnbtn.setOnClickListener {
            var database = Firebase.database
            val myRef = database.getReference("Journal Entry")
            myRef.setValue(dailyentry)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}