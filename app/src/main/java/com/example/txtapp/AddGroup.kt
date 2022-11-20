package com.example.txtapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties

import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_group.*

import java.util.HashMap


class AddGroup : AppCompatActivity() {

    private lateinit var addGroupName: EditText
    private lateinit var addGroupMember: EditText
    private lateinit var saveButton: Button
    private lateinit var addGroupButton: Button

    private lateinit var groupRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_group)
        var memberList = ArrayList<String>()
        val myAdapter = AddGroupAdapter(this, memberList)

        //assign
        wireWidgets()
        groupRef = FirebaseDatabase.getInstance().getReference()

        var user = Firebase.auth.currentUser
        var email = user?.email?.replace(".", "|")


        addGroupButton.setOnClickListener{
            memberList.add(addGroupMember.text.toString())
            myAdapter.notifyDataSetChanged()
            forTest.setText(addGroupMember.text.toString())
        }


        recyclerView_addedMember.adapter = myAdapter
        recyclerView_addedMember.layoutManager = LinearLayoutManager(this)

        saveButton.setOnClickListener{
            val groupName = addGroupName.text.toString()

            for(member in memberList)
            {
                groupRef.child("Users").child(member.replace(".","|")).child("groups").child(groupName).setValue("")
                groupRef.child("Groups").child(groupName).child(member.replace(".","|")).setValue("")
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }



    private fun wireWidgets() {
        addGroupName = findViewById(R.id.editText_groupName)        //edittext for groupName
        addGroupMember = findViewById(R.id.editText_username)       //editText for a group Member email
        saveButton = findViewById(R.id.button_saveGroup)            //save group
        addGroupButton = findViewById(R.id.button_addMember)        //add member button
    }
}
