package com.example.txtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var edtemail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtname: EditText
    private lateinit var btnsignup: Button
    private lateinit var phAuth: FirebaseAuth

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        phAuth = FirebaseAuth.getInstance()
        edtemail = findViewById(R.id.email)
        edtPassword = findViewById(R.id.password)
        edtname = findViewById(R.id.name)
        btnsignup = findViewById(R.id.signup_btn)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")


        btnsignup.setOnClickListener {
            val email = edtemail.text.toString()
            val password = edtPassword.text.toString()

            val userID = dbRef.push().key!!

            val sampleUser = UserModel(userID, email)


            dbRef.child(userID).setValue(sampleUser)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data Inserted successfully", Toast.LENGTH_LONG).show()
                }.addOnFailureListener{ err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
            signup(email, password)
        }
    }
    private fun signup(email: String, password: String){
        //creating user
        phAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //jump to home act
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "Some error has occured", Toast.LENGTH_SHORT).show()

                }
            }

    }
}