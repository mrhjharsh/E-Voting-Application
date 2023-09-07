package com.example.e_votingapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class admin_activity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
            startActivity(Intent(this , login_page::class.java))
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again", Toast.LENGTH_SHORT).show()

        // Reset the flag after a delay (e.g., 2 seconds)
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        supportActionBar?.hide()

        var bjp : TextView = findViewById(R.id.bjpvotes)
        var total : TextView = findViewById(R.id.username)
        var aap : TextView = findViewById(R.id.aapvotes)
        var inc : TextView = findViewById(R.id.incvotes)
        var bsp : TextView = findViewById(R.id.bspvotes)
        var aadhaar_id : CardView = findViewById(R.id.aadhaar_id)
        var cpi : TextView = findViewById(R.id.cpivotes)
        bjp.text = splash.bjpv.toString()+""
        aap.text = splash.aapv.toString()+""
        inc.text = splash.incv.toString()+""
        bsp.text = splash.bspv.toString()+""
        cpi.text = splash.cpiv.toString()+""
        total.text = splash.totalvote.toString()+""
        aadhaar_id.setOnClickListener {
startActivity(Intent(this
 , admin_activity2::class.java))
        }

    }
}