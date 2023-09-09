package com.example.e_votingapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class admin_activity2 : AppCompatActivity() {

    override fun onBackPressed() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin2)
        supportActionBar?.hide()
        var rv: RecyclerView = findViewById(R.id.list)
        val ca = custom_adapter()
        rv.layoutManager = LinearLayoutManager(applicationContext)
        rv.adapter = ca
    }

}