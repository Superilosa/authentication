package com.example.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        init()
    }
    private fun init(){
        auth = Firebase.auth
        logInButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){
                logInProgress.visibility = View.VISIBLE
                logInButton.isClickable = false
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        logInProgress.visibility = View.GONE
                        logInButton.isClickable = true
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("logIn", "signInWithEmail:success")
                            Toast.makeText(this,"Logged in successfully", Toast.LENGTH_LONG).show()
                            val user = auth.currentUser
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("logIn", "signInWithEmail:failure", task.exception)
                            Toast.makeText(this, "Failed to log in." + task.exception,
                                Toast.LENGTH_LONG).show()
                        }
                    }

            }else{
                Toast.makeText(this,"Please fill all fields", Toast.LENGTH_LONG).show()
            }
        }
    }
}