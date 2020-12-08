package com.example.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init(){
        auth = Firebase.auth
        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val password2 = password2Input.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty()){
                if(password == password2){
                    registerProgress.visibility = View.VISIBLE
                    registerButton.isClickable = false
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                registerProgress.visibility = View.GONE
                                registerButton.isClickable = true
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("register", "createUserWithEmail:success")
                                    Toast.makeText(this,"Registered Successfully", Toast.LENGTH_LONG).show()
                                    val user = auth.currentUser
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("register", "createUserWithEmail:failure", task.exception)
                                    Toast.makeText(this, "Registration failed." + task.exception,
                                            Toast.LENGTH_LONG).show()
                                }
                            }


                }else{
                    Toast.makeText(this,"Passwords do not match", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
            }
        }
    }
}