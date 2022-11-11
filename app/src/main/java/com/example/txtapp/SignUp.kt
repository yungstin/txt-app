package com.example.txtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
        wireWidgets()
        edtemail.setText(getIntent().getStringExtra("email"));
        edtPassword.setText(getIntent().getStringExtra("password"));
        dbRef = FirebaseDatabase.getInstance().getReference("Users")


        edtname.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                compileUser()
                return@OnKeyListener true
            }
            false
        })

        btnsignup.setOnClickListener {
            compileUser()
        }
    }

    private fun compileUser(){
        val email = edtemail.text.toString()
        val password = edtPassword.text.toString()

        val userID = ""
        val Date = ""
        //name isn't used????
        val sampleUser = UserModel(userID, email)

        dbRef.child(email.replace(".", "|")).setValue(sampleUser)
            .addOnCompleteListener {
                Toast.makeText(this, "Data Inserted successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
        signup(email, password)
    }

    private fun wireWidgets() {
        phAuth = FirebaseAuth.getInstance()
        edtemail = findViewById(R.id.editText_email)
        edtPassword = findViewById(R.id.editText_password)
        edtname = findViewById(R.id.editText_username)
        btnsignup = findViewById(R.id.button_signup)
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