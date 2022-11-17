package com.example.txtapp

import RecyclerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.api.Distribution.BucketOptions.Linear
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var btndailyjournal: Button
    private lateinit var btnAddGroup: FloatingActionButton


    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imageList = mutableListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wireWidgets()
        postToList()

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = RecyclerAdapter(titleList, descList, imageList)

        btndailyjournal = findViewById(R.id.button_dailyjournal)
        btndailyjournal.setOnClickListener {
            val intent = Intent(this, DailyJournal::class.java)
            startActivity(intent)
        }


        btnAddGroup = findViewById(R.id.button_addUser)
        btnAddGroup.setOnClickListener{
            val intent = Intent(this, AddGroup::class.java)
            startActivity(intent)
        }



    }

    private fun wireWidgets() {
        btndailyjournal = findViewById(R.id.button_dailyjournal)
        btnAddGroup = findViewById(R.id.button_addUser)
    }

    private fun addToList(title: String, description: String, image: Int)
    {
        titleList.add("test")
        descList.add(description)
        imageList.add(image)
    }


    private fun postToList()
    {
        for(i in 1..25)
        {
            addToList("Title $i", "Description $i", R.mipmap.ic_launcher)
        }
    }


}