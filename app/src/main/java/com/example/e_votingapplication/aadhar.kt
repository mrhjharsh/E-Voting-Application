package com.example.e_votingapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText

class aadhar : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false
    companion object{
        var aadhar_id = "111111111111"
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again", Toast.LENGTH_SHORT).show()

        // Reset the flag after a delay (e.g., 2 seconds)
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aadhar)
        supportActionBar?.hide()
        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView2)
        lottieAnimationView.setAnimation(R.raw.aadharlottie)
        lottieAnimationView.playAnimation()
        lottieAnimationView.loop(true)
        var username: TextView = findViewById(R.id.username)
        var aadhar: TextInputEditText = findViewById(R.id.aadhar)
        var button: CardView = findViewById(R.id.confirm)
        var userphone: TextView = findViewById(R.id.userphone)
        username.setText("" + login_page.username)
        userphone.setText("" + login_page.usernumber)
        button.setOnClickListener {
            if(aadhar.text.toString().isEmpty() || aadhar.text.toString().length != 12 ){
                Toast.makeText(this, "Please Provide Valid Aadhaar ID", Toast.LENGTH_SHORT).show()
            }
            else{
                aadhar_id = aadhar.text.toString()
                finish()
                startActivity(Intent(this , welcome_screen::class.java))
            }
        }
    }
}