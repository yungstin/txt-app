package com.example.txtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.group_entry_journals.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var btndailyjournal: Button
    private lateinit var btnAddGroup: FloatingActionButton

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        layoutManager = LinearLayoutManager(this)

        rv_recyclerView.layoutManager = layoutManager


        adapter = RecyclerAdapter()
        rv_recyclerView.adapter = adapter

        btndailyjournal = findViewById(R.id.dailyjournal)
        btndailyjournal.setOnClickListener {
            val intent = Intent(this, DailyJournal::class.java)
            startActivity(intent)
        }



        btnAddGroup = findViewById(R.id.addUserBtn)
        btnAddGroup.setOnClickListener{
            val intent = Intent(this, AddGroup::class.java)
            startActivity(intent)
        }




    }
}