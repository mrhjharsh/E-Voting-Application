package com.example.e_votingapplication

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class signup_page : AppCompatActivity() {

    override fun onBackPressed() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)
        supportActionBar?.hide()

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView2)
        lottieAnimationView.setAnimation(R.raw.loginpage)
        lottieAnimationView.playAnimation()
        lottieAnimationView.loop(true)
        FirebaseApp.initializeApp(this)
        var name: TextInputEditText = findViewById<TextInputEditText>(R.id.name)
        var password: TextInputEditText = findViewById<TextInputEditText>(R.id.password)
        var confirm_passowrd: TextInputEditText =
            findViewById<TextInputEditText>(R.id.confirm_password)
        var email: TextInputEditText = findViewById<TextInputEditText>(R.id.email)
        var mobile: TextInputEditText = findViewById<TextInputEditText>(R.id.mobile)
        var signup: CardView = findViewById(R.id.signup)
        signup.setOnClickListener {
            if (name.text.toString().isEmpty() || password.text.toString()
                    .isEmpty() || confirm_passowrd.text.toString().isEmpty() ||
                email.text.toString().isEmpty() || mobile.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Fill All Column", Toast.LENGTH_SHORT).show()
            } else {
                if (!confirm_passowrd.text.toString().equals(password.text.toString())) {
                    Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show()
                } else {
                    val database = FirebaseDatabase.getInstance()
                    val myRef = database.getReference("e-voting application database")
                    var cutted_email: String = ""
                    for (i in email.text.toString()) {
                        if (i == '@') break
                        cutted_email += i
                    }
                    myRef.child(cutted_email.toString())
                        .setValue(
                            password.text.toString().trim() + ", " + name.text.toString()
                                .trim() + ", " + mobile.text.toString()
                                .trim() + ", " + "0".toString().trim() + ", "
                        )
                        .addOnFailureListener {
                            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnSuccessListener {
                            Handler().postDelayed({
                                finish()
                                // startActivity(Intent(this@signup_page , login_page::class.java))
                            }, 500)
                            Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
                        }
                }

            }
        }


    }
}