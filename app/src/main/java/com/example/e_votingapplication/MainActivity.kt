package com.example.e_votingapplication

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    companion object{
        var totalvote: Int = 0
    }
    var party: Int = 0
    var bjpv: Int = 0
    var aapv: Int = 0
    var bspv: Int = 0
    var vote: Int = 0
    var cpiv: Int = 0
    var incv: Int = 0
    var perv: String = ""


    private var doubleBackToExitPressedOnce = false
    private fun clearApplicationData(context: Context) {
        val packageName = context.packageName
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val packageManager = context.packageManager

        val intent = packageManager.getLaunchIntentForPackage(packageName)
        val componentName = intent?.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)

        activityManager.appTasks.forEach { it.finishAndRemoveTask() }
        context.startActivity(mainIntent)
    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            if(vote == 1){
                startActivity(Intent(this , splash::class.java))
                finish()
            }
            else finish()
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again", Toast.LENGTH_SHORT).show()


        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView2)
        lottieAnimationView.setAnimation(R.raw.lottie_animation)
        lottieAnimationView.playAnimation()
        lottieAnimationView.loop(true)
        var bjp: CardView = findViewById(R.id.bjp)
        var aap: CardView = findViewById(R.id.aap)
        var bsp: CardView = findViewById(R.id.bsp)
        var congress: CardView = findViewById(R.id.INC)
        var cpi: CardView = findViewById(R.id.CPI)
        //read data for total vote
        val database2 = Firebase.database
        val myRef2 = database2.getReference("e-voting application database")
        myRef2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    totalvote =
                        dataSnapshot.child("Totalvote").getValue(String::class.java).toString()
                            .toInt()
                    perv =  dataSnapshot.child(login_page.ce).getValue(String::class.java).toString()
                    bjpv = dataSnapshot.child("bjp").getValue(String::class.java).toString().toInt()
                    aapv = dataSnapshot.child("aap").getValue(String::class.java).toString().toInt()
                    cpiv = dataSnapshot.child("cpi").getValue(String::class.java).toString().toInt()
                    incv = dataSnapshot.child("inc").getValue(String::class.java).toString().toInt()
                    bspv = dataSnapshot.child("bsp").getValue(String::class.java).toString().toInt()
                } else {
                    Toast.makeText(this@MainActivity, "Something Went Wrong !", Toast.LENGTH_SHORT)
                        .show()
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Something Went Wrong !", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        bjp.setOnClickListener {
            party = 1
            if (login_page.status.equals("0")) showCustomDialog()
            else {
                Toast.makeText(this@MainActivity, "You have already voted !", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        aap.setOnClickListener {
            party = 2
            if (login_page.status.equals("0")) showCustomDialog()
            else {
                Toast.makeText(this@MainActivity, "You have already voted !", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        bsp.setOnClickListener {
            party = 3
            if (login_page.status.equals("0")) showCustomDialog()
            else {
                Toast.makeText(this@MainActivity, "You have already voted !", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        congress.setOnClickListener {
            party = 4
            if (login_page.status.equals("0")) showCustomDialog()
            else {
                Toast.makeText(this@MainActivity, "You have already voted !", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        cpi.setOnClickListener {
            party = 5
            if (login_page.status.equals("0")) showCustomDialog()
            else {
                Toast.makeText(this@MainActivity, "You have already voted !", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showCustomDialog() {
        FirebaseApp.initializeApp(this)
        val dialogView = layoutInflater.inflate(R.layout.activity_dialogbox, null)
        val dialogText = dialogView.findViewById<TextView>(R.id.dialogText)
        dialogText.text = "Confirm Your Vote ! "

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
            .setPositiveButton("BACK") {dialog, _ ->
                // Handle positive button click
                dialog.cancel()
            }
            .setNegativeButton("CONFIRM") { dialog, _ ->
                vote = 1
                // writing data to total
                totalvote++
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("e-voting application database")
                myRef.child("Totalvote")
                    .setValue((totalvote).toString())
                    .addOnFailureListener {
                    }.addOnSuccessListener {
                    }
                var partyname = ""
                //write to party
                if (party == 1) {
                    //write at status
                    //
                    partyname = "bjp"




                    login_page.status = "1"
                    myRef.child(login_page.ce.toString())
                        .setValue(perv.substring(0,perv.length-3)+"1"+perv.substring(perv.length-2 , perv.length))
                        .addOnFailureListener {
//                            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT)
//                                .show()
                        }.addOnSuccessListener {
                            //Toast.makeText(this, ""+perv.substring(0,perv.length), Toast.LENGTH_SHORT).show()
                        }

                    bjpv++
                    myRef.child("bjp")
                        .setValue((bjpv).toString())
                        .addOnFailureListener {
                            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnSuccessListener {
                            Toast.makeText(this, "Voted Successful", Toast.LENGTH_SHORT).show()
                        }
                }
                if (party == 2) {
                    partyname = "aap"

                    login_page.status = "1"
                    myRef.child(login_page.ce.toString())
                        .setValue(perv.substring(0,perv.length-3)+"1"+perv.substring(perv.length-2 , perv.length))
                        .addOnFailureListener {
//                            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT)
//                                .show()
                        }.addOnSuccessListener {
                            //Toast.makeText(this, ""+perv.substring(0,perv.length), Toast.LENGTH_SHORT).show()
                        }

                    aapv++
                    myRef.child("aap")
                        .setValue((aapv).toString())
                        .addOnFailureListener {
                            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnSuccessListener {
                            Toast.makeText(this, "Voted Successful", Toast.LENGTH_SHORT).show()
                        }
                }
                if (party == 3) {
                    partyname = "bsp"

                    login_page.status = "1"
                    myRef.child(login_page.ce.toString())
                        .setValue(perv.substring(0,perv.length-3)+"1"+perv.substring(perv.length-2 , perv.length))
                        .addOnFailureListener {
//                            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT)
//                                .show()
                        }.addOnSuccessListener {
                            //Toast.makeText(this, ""+perv.substring(0,perv.length), Toast.LENGTH_SHORT).show()
                        }
                    bspv++
                    myRef.child("bsp")
                        .setValue((bspv).toString())
                        .addOnFailureListener {
                            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnSuccessListener {
                            Toast.makeText(this, "Voted Successful", Toast.LENGTH_SHORT).show()
                        }
                }
                if (party == 4) {
                    partyname = "inc"

                    login_page.status = "1"
                    myRef.child(login_page.ce.toString())
                        .setValue(perv.substring(0,perv.length-3)+"1"+perv.substring(perv.length-2 , perv.length))
                        .addOnFailureListener {
//                            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT)
//                                .show()
                        }.addOnSuccessListener {
                            //Toast.makeText(this, ""+perv.substring(0,perv.length), Toast.LENGTH_SHORT).show()
                        }
                    incv++
                    myRef.child("inc")
                        .setValue((incv).toString())
                        .addOnFailureListener {
                            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnSuccessListener {
                            Toast.makeText(this, "Voted Successful", Toast.LENGTH_SHORT).show()
                        }
                }
                if (party == 5) {
                    partyname = "cpi"

                    login_page.status = "1"
                    myRef.child(login_page.ce.toString())
                        .setValue(perv.substring(0,perv.length-3)+"1"+perv.substring(perv.length-2 , perv.length))
                        .addOnFailureListener {
//                            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT)
//                                .show()
                        }.addOnSuccessListener {
                            //Toast.makeText(this, ""+perv.substring(0,perv.length), Toast.LENGTH_SHORT).show()
                        }
                    cpiv++
                    myRef.child("cpi")
                        .setValue((cpiv).toString())
                        .addOnFailureListener {
                            Toast.makeText(this, "Something Went Wrong !", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnSuccessListener {
                            Toast.makeText(this, "Voted Successful", Toast.LENGTH_SHORT).show()
                        }
                }
                myRef.child("aadhar_id_vote").child(login_page.username)
                    .setValue(partyname.toString())
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }
}