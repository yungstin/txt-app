package com.example.txtapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class DailyJournal : AppCompatActivity() {
    private lateinit var exit: Button
    private lateinit var saveJournal: Button
    private lateinit var date: EditText
    private lateinit var journalTitle: EditText
    private lateinit var journalBody: EditText


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.dailyjournal)
        wireWidgets();
        date.setText( getCurrentDate());
        date.setFocusable(false);//later want to set onclick to open up calender to check previous dates

        date.setOnClickListener{
            Toast.makeText(this, "Open up to a calender to view past dates", Toast.LENGTH_SHORT).show()
        }

        saveJournal.setOnClickListener{
            val dailyentry = journalBody.text.toString()
            var database = Firebase.database("https://fapwf-f785f-default-rtdb.firebaseio.com/")
            val myRef = database.getReference("Journal Entry")
            //Toast.makeText(this@DailyJournal, dailyentry, Toast.LENGTH_SHORT).show()
            myRef.setValue(dailyentry)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }//test
        exit.setOnClickListener{
            Toast.makeText(this, "exits this journal gives user prompt to either save or discard journal they have written", Toast.LENGTH_SHORT).show()
        }
    }
    private fun wireWidgets()
    {
        exit = findViewById(R.id.button_exit);
        saveJournal = findViewById(R.id.button_saveJournal);
        date = findViewById(R.id.editTextDate_date);
        journalTitle = findViewById(R.id.editText_Title);
        journalBody = findViewById(R.id.editText_journalBody);

    }


    fun getCurrentDate(): String? {
        val c: Calendar = Calendar.getInstance()
        System.out.println("Current time => " + c.getTime())
        val df = SimpleDateFormat("MM/dd/yyyy")
        return df.format(c.getTime())
    }





}