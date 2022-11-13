package com.example.txtapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.database.DatabaseError;
import kotlin.collections.ArrayList


class DailyJournal : AppCompatActivity() {
    private lateinit var exit: Button
    private lateinit var saveJournal: Button
    private lateinit var date: EditText
    private lateinit var journalTitle: EditText
    private lateinit var journalBody: TextView
    private lateinit var dbRef: DatabaseReference
    private lateinit var enterytext : TextView
    lateinit var datebasejournal : String

    private var datePickerDialog: DatePickerDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.dailyjournal)

        wireWidgets();
        initDatePicker();
        date.setText(getTodaysDate());
        date.setFocusable(false);
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        var user = Firebase.auth.currentUser
        var email = (user?.email)?.replace(".", "|")

        val entryListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val dailyentry = journalBody.text.toString()

                val post = dataSnapshot.child("$email").child(getCurrentDate()).child("journalentry").value

                if(post != null){
                journalBody.text = post as CharSequence}

            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }



        dbRef.addValueEventListener(entryListener)



        saveJournal.setOnClickListener{
            val dailyentry = journalBody.text.toString()
            var finalentry = "$dailyentry"
            Log.i("this is final entry", finalentry)

            var user = Firebase.auth.currentUser
            var uid = user?.uid
            var email = (user?.email)?.replace(".", "|")
            dbRef = FirebaseDatabase.getInstance().getReference("Users")
            dbRef.child("$email").child(getCurrentDate()).child("journalentry").setValue(finalentry)
            Log.i("this is alright i guess", "$email, ${getCurrentDate()}")
            dbRef.removeEventListener(entryListener)

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

    fun getCurrentDate(): String {
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







    private fun wireWidgets() {
        exit = findViewById(R.id.button_exit);
        saveJournal = findViewById(R.id.button_saveJournal);
        date = findViewById(R.id.editTextDate_date);
        journalBody = findViewById(R.id.editText_journalBody);
    }

}


