package com.example.txtapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.HashMap

class AddGroup : AppCompatActivity() {

    private lateinit var addUser: EditText
    private lateinit var addGroup: EditText
    private lateinit var addButton: Button
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_group)
        dbRef = FirebaseDatabase.getInstance().getReference("Groups")
        addGroup = findViewById(R.id.addGroupname)
        addUser = findViewById(R.id.addUserName)



        addButton = findViewById(R.id.addUserBtn)
        addButton.setOnClickListener{
            val addgroup = addGroup.text.toString()
            val adduser = addUser.text.toString()
            addgroupname(addgroup, adduser)
            // this should be moved into the main act and not in the add group
//            val intent = Intent(this, GroupJournal::class.java)
//            startActivity(intent)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    @IgnoreExtraProperties
    data class Userinfo(
        var user: String?,
        var stars: MutableMap<String, Boolean> = HashMap()
    ){
        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "$user" to user,
                "stars" to stars
            )
        }
    }

    private fun addgroupname(addgroup: String?, adduser: String?) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        var user = adduser?.replace(".", "|")
        val key = dbRef.child("Groups").push().key
        if (key == null) {
            Toast.makeText(this, "Sent to database sucessfully", Toast.LENGTH_SHORT).show()
            return
        }
        val entry = Userinfo(user)
        val postValues = entry.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "$addgroup/$user" to postValues,
        )

        dbRef.updateChildren(childUpdates)
    }










}