package com.example.txtapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddGroup : AppCompatActivity() {

    private lateinit var addUser: EditText
    private lateinit var addButton: Button
    private lateinit var rvView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_group)


        val nameEntry = addUser.text.toString()


        addButton = findViewById(R.id.addUserBtn)
        addButton.setOnClickListener{
            val intent = Intent(this, GroupJournal::class.java)
            startActivity(intent)
        }



    }

}