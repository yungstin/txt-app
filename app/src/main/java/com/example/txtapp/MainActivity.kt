package com.example.txtapp

import RecyclerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var btndailyjournal: Button
    private lateinit var btnAddGroup: FloatingActionButton

    private lateinit var test: TextView
    private lateinit var dbUserRef: DatabaseReference
    private lateinit var dbGroupRef: DatabaseReference

    private lateinit var userList: ArrayList<RecyclerViewHolder>
    private lateinit var groupRecycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wireWidgets()
        initializeButtons()


        groupRecycleView = findViewById(R.id.recyclerview)
        groupRecycleView.layoutManager = LinearLayoutManager(this)
        groupRecycleView.setHasFixedSize(true)

        userList = arrayListOf<RecyclerViewHolder>()


        var email = (Firebase.auth.currentUser?.email)?.replace(".", "|").toString()
        dbGroupRef = FirebaseDatabase.getInstance().getReference("Groups")


        dbUserRef = FirebaseDatabase.getInstance().getReference("Users")
        dbUserRef.child(email).get().addOnSuccessListener {
            if (it.exists()) {
                var temp = it.child("groups").value.toString().split(",").toTypedArray()
                var i = 0
                for(userSnap in it.child("groups").children)
                {
                    val temp2 = RecyclerViewHolder(temp[i], "HEELLO", R.mipmap.ic_launcher)
                    userList.add(temp2)
                    i++
                }
                val mAdapter = RecyclerAdapter(userList)
                groupRecycleView.adapter = mAdapter
            }
            else {
            }
        }

    }

    private fun initializeButtons()
    {
        btnAddGroup = findViewById(R.id.button_addUser)
        btnAddGroup.setOnClickListener {
            val intent = Intent(this, AddGroup::class.java)
            startActivity(intent)
        }

        btndailyjournal = findViewById(R.id.button_dailyjournal)
        btndailyjournal.setOnClickListener {
            val intent = Intent(this, DailyJournal::class.java)
            startActivity(intent)
        }

    }


    private fun wireWidgets() {
        btndailyjournal = findViewById(R.id.button_dailyjournal)
        btnAddGroup = findViewById(R.id.button_addUser)
        test = findViewById(R.id.test_TextView)

    }




}