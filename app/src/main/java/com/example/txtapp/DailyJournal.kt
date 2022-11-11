package com.example.txtapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
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

    private var datePickerDialog: DatePickerDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dailyjournal)
        wireWidgets();
        initDatePicker();
        date.setText(getTodaysDate());
        date.setFocusable(false);
        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        //this is the second time im doing this
        //i dont know wut what is happening
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

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                val date = makeDateString(day, month, year)
                this.date.setText(date)
            }

        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]

        val style: Int = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
        datePickerDialog!!.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    fun getCurrentDate(): String? {
        val c: Calendar = Calendar.getInstance()
        System.out.println("Current time => " + c.getTime())
        val df = SimpleDateFormat("MM|dd|yyyy")
        return df.format(c.getTime())
    }

    fun openDatePicker(view: View?) {
        datePickerDialog?.show()
    }

    private fun getTodaysDate(): String? {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        var month = cal[Calendar.MONTH]
        month = month + 1
        val day = cal[Calendar.DAY_OF_MONTH]
        return makeDateString(day, month, year)
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String? {
        return "$month/$day/$year"
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



    private fun wireWidgets()
    {
        exit = findViewById(R.id.button_exit);
        saveJournal = findViewById(R.id.button_saveJournal);
        date = findViewById(R.id.editTextDate_date);
        journalTitle = findViewById(R.id.editText_Title);
        journalBody = findViewById(R.id.editText_journalBody);

    }

}