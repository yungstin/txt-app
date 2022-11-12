package com.example.txtapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var edtemail: EditText
    lateinit var edtPassword: EditText
    lateinit var btnlogin: Button
    lateinit var btnsignup: Button
    private val EMAIL = "email"
    private val PASSWORD = "password"
    private lateinit var phAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        wireWidgets()

        phAuth = FirebaseAuth.getInstance()
        //testing
        
        btnsignup.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }


        btnlogin.setOnClickListener {
            loginHandler()
        }

        edtPassword.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                loginHandler()
                return@OnKeyListener true
            }
            false
        })

    }

    private fun loginHandler(){
        val phone = edtemail.text.toString()
        val password = edtPassword.text.toString()
        val email = UserModel("", phone)
        try {
            login(phone, password);
        }catch (e: Exception) {
            createUser()
        }

    }

    private fun createUser(){
        val messageEmail: String = edtemail.getText().toString()
        val messagePassword: String = edtPassword.getText().toString()

        val intentSendInfo = Intent(this@Login, SignUp::class.java)

        intentSendInfo.putExtra(EMAIL,messageEmail)
        intentSendInfo.putExtra(PASSWORD,messagePassword)
        startActivity(intentSendInfo)
    }

    private fun login(email: String, password: String){
        phAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, "User does not exist", Toast.LENGTH_SHORT).show()

                }
            }

    }

    private fun wireWidgets() {
        edtemail = findViewById(R.id.editText_email)
        edtPassword = findViewById(R.id.editText_password)
        btnlogin = findViewById(R.id.button_login)
        btnsignup = findViewById(R.id.button_signup)
    }
}
