package com.example.e_votingapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText
import kotlin.system.exitProcess

class login_page : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            exitProcess(0)
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()

        // Reset the flag after a delay (e.g., 2 seconds)
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
    companion object {
        var username: String = ""
        var usernumber: String = ""
        var status: String = "0"
        var useremail: String = ""
        var ce: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        supportActionBar?.hide()

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView2)
        lottieAnimationView.setAnimation(R.raw.loginpage)
        lottieAnimationView.playAnimation()
        lottieAnimationView.loop(true)
        var signup: CardView = findViewById(R.id.signup);
        var email: TextInputEditText = findViewById(R.id.email)
        var password: TextInputEditText = findViewById(R.id.passwords)
        signup.setOnClickListener {
            startActivity(Intent(applicationContext, signup_page::class.java));
        }
        var login: CardView = findViewById(R.id.login);
        login.setOnClickListener {
            var c = 0
            if (email.text.toString().equals("admin@gmail.com") && password.text.toString().equals("admin")) {
                c = 1
                Handler().postDelayed({
                    startActivity(Intent(applicationContext, admin_activity::class.java))
                    finish()
                },1000)
                Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show()
            }

            if (email.text.toString().isEmpty() || password.text.toString().isEmpty()) {
                Toast.makeText(this, "Fill Both Column", Toast.LENGTH_SHORT).show()
            } else {
                var p = password.text.toString()
                var cutted_email: String = ""
                for (i in email.text.toString()) {
                    if (i == '@') break
                    cutted_email += i
                }
                var e = cutted_email
               ce = cutted_email

                for (i in 0 until databaseclass.email.size) {
                    if (e.trim().equals(databaseclass.email.get(i)) && p.trim()
                            .equals(databaseclass.password.get(i))
                    ) {
                        c = 1
                        username = databaseclass.name.get(i)
                        usernumber = databaseclass.mobile.get(i)
                        useremail = databaseclass.email.get(i)
                        status = databaseclass.status.get(i)
                        Handler().postDelayed({
                            finish()
                            startActivity(Intent(applicationContext, aadhar::class.java))
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        }, 500)

                    }
                }

                if (c == 0) Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()

            }

        }

    }
}