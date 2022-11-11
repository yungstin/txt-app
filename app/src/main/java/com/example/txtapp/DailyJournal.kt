package com.example.txtapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
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
    private lateinit var database: DatabaseReference
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dailyjournal)
        wireWidgets();
        date.setText( getCurrentDate());
        date.setFocusable(false);//later want to set onclick to open up calender to check previous dates

        date.setOnClickListener{
            Toast.makeText(this, "Open up to a calender to view past dates", Toast.LENGTH_SHORT).show()
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        //this is a test comment
        //this is a new test comment
        saveJournal.setOnClickListener{
            val dailyentry = journalBody.text.toString()
            database = Firebase.database.reference
            var user = Firebase.auth.currentUser
            var uid = user?.uid
            var email = user?.email
            uploadentry(uid, email, dailyentry)


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
        val df = SimpleDateFormat("MM|dd|yyyy")
        return df.format(c.getTime())
    }
    @IgnoreExtraProperties
    data class Userinfo(
        var journalentry: String,
        var stars: MutableMap<String, Boolean> = HashMap()
    ){
        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "journalentry" to journalentry,
                "stars" to stars
            )
        }
    }

    private fun uploadentry(uid: String?, email: String?, journalentry :String) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        var email = email?.replace(".", "|")
        val key = database.child("Users/$email").push().key
        if (key == null) {
            Toast.makeText(this, "Sent to database sucessfully", Toast.LENGTH_SHORT).show()
            return
        }
        val entry = Userinfo(journalentry)
        val postValues = entry.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/Users/$email/${getCurrentDate()}  " to postValues,
        )

        database.updateChildren(childUpdates)
    }





}