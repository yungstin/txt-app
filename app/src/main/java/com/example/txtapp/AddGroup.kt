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
import com.google.firebase.ktx.Firebase

class AddGroup : AppCompatActivity() {

    private lateinit var addGroupName: EditText
    private lateinit var addGroupMember: EditText
    private lateinit var addButton: Button

    private lateinit var groupRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_group)

        addGroupName = findViewById(R.id.addGroupname)
        addGroupMember = findViewById(R.id.addUserName)




        groupRef = FirebaseDatabase.getInstance().getReference("Groups")

        addButton = findViewById(R.id.addUserBtn)
        addButton.setOnClickListener{
            val groupName = addGroupName.text.toString()
            val memberName = addGroupMember.text.toString()

            groupadd(groupName,memberName)

            val intent = Intent(this, GroupJournal::class.java)
            startActivity(intent)


//                .addOnCompleteListener {
//                    Toast.makeText(this, "Data Inserted successfully", Toast.LENGTH_LONG).show()
//                }.addOnFailureListener{ err ->
//                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
//                }

        }


    }


    @IgnoreExtraProperties
    data class Userinfo(
        var memberName: String?,
        var stars: MutableMap<String, Boolean> = HashMap()
    ){
        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "$memberName" to memberName,
                "stars" to stars
            )
        }
    }

    private fun groupadd(groupName: String?, memberName: String?) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        var memberName = memberName?.replace(".", "|")
        val key = groupRef.child("Groups/$groupName").push().key
        if (key == null) {
            Toast.makeText(this, "Sent to database sucessfully", Toast.LENGTH_SHORT).show()
            return
        }
        val entry = Userinfo(memberName)
        val postValues = entry.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/Users/$groupName" to postValues,
        )

        groupRef.updateChildren(childUpdates)
    }
}