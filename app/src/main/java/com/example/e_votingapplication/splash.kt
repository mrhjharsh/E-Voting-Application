package com.example.e_votingapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class splash : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    companion object {
        var totalvote: Int = 0
        var bjpv: Int = 0
        var aapv: Int = 0
        var bspv: Int = 0
        var cpiv: Int = 0
        var incv: Int = 0
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            System.exit(0)
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()

        // Reset the flag after a delay (e.g., 2 seconds)
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val serviceIntent = Intent(this, InternetCheckService::class.java)
        startService(serviceIntent)

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        lottieAnimationView.setAnimation(R.raw.lottie_animation)
        lottieAnimationView.playAnimation()
        Handler().postDelayed({
            finish()
            val i = Intent(applicationContext, login_page::class.java)
            startActivity(i)
        }, 4000)

        FirebaseApp.initializeApp(this)

        val database2 = Firebase.database
        val myRef2 = database2.getReference("e-voting application database")
        myRef2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    totalvote =
                        dataSnapshot.child("Totalvote").getValue(String::class.java).toString()
                            .toInt()
                    bjpv = dataSnapshot.child("bjp").getValue(String::class.java).toString().toInt()
                    aapv = dataSnapshot.child("aap").getValue(String::class.java).toString().toInt()
                    cpiv = dataSnapshot.child("cpi").getValue(String::class.java).toString().toInt()
                    incv = dataSnapshot.child("inc").getValue(String::class.java).toString().toInt()
                    bspv = dataSnapshot.child("bsp").getValue(String::class.java).toString().toInt()
                } else {
                    Toast.makeText(this@splash, "Something Went Wrong !", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@splash, "Something Went Wrong !", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        //who vote whom ?

        val myRef3 = database2.getReference("e-voting application database").child("aadhar_id_vote")
        myRef3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (i in dataSnapshot.children){
                        databaseclass.voter_name.add(i.key.toString())
                        databaseclass.voter_party.add(i.value.toString())
                    }
                } else {
                    Toast.makeText(this@splash, "Something Went Wrong !", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@splash, "Something Went Wrong !", Toast.LENGTH_SHORT)
                    .show()
            }
        })











        val database = Firebase.database
        val myRef = database.getReference("e-voting application database")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    var ss = ""
                    for (i in dataSnapshot.children) {
                        if (i.key.toString().equals("Totalvote") || i.key.toString()
                                .equals("aap") ||
                            i.key.toString().equals("bjp") || i.key.toString().equals("cpi") ||
                            i.key.toString().equals("inc") ||
                            i.key.toString().equals("bsp")|| i.key.toString().equals("aadhar_id_vote")
                        ) {
                        } else {
                            databaseclass.email.add(i.key.toString())
                            ss = i.value.toString()
                            var p = ""
                            var k = 0
                            for (i in ss.toString()) {
                                if (i == ',') {
                                    if (k == 0) databaseclass.password.add(p.trim())
                                    if (k == 1) databaseclass.name.add(p.trim())
                                    if (k == 2) databaseclass.mobile.add(p.trim())
                                    if (k == 3) databaseclass.status.add(p.trim())
                                    k++
                                    p = ""
                                    continue
                                }
                                p += i
                            }


                        }
                    }
                } else {

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@splash, "Something Went Wrong !", Toast.LENGTH_SHORT).show()
            }
        })


    }
}