package com.example.txtapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties

import com.google.firebase.ktx.Firebase

import java.util.HashMap


class AddGroup : AppCompatActivity() {

    private lateinit var addGroupName: EditText
    private lateinit var addGroupMember: EditText
    private lateinit var addButton: Button

    private lateinit var groupRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_group)


        addGroupName = findViewById(R.id.editText_groupName)
        addGroupMember = findViewById(R.id.editText_username)




        groupRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fapwf-f785f-default-rtdb.firebaseio.com/")

        addButton = findViewById(R.id.button_addUser)


        addButton.setOnClickListener{
            val groupName = addGroupName.text.toString()
            val memberName = addGroupMember.text.toString()
            var user = Firebase.auth.currentUser
            var email = user?.email?.replace(".", "|")
            groupRef.child("Users").child(email as String).child("groups").child(groupName).setValue("")
            groupRef.child("Groups").child(groupName).child(memberName.replace(".","|")).setValue("")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }



    private fun wireWidgets() {
        addGroupName = findViewById(R.id.editText_groupName)
        addGroupMember = findViewById(R.id.editText_username)
        addButton = findViewById(R.id.button_addUser)
    }
}
