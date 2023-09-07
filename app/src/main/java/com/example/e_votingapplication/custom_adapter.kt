package com.example.e_votingapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class custom_adapter : RecyclerView.Adapter<ViewHolder>{

var unf : List<String> = ArrayList()
var upf : List<String> = ArrayList()


    constructor(){

       unf = ArrayList(databaseclass.voter_name)
        upf= ArrayList(databaseclass.voter_party)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_custom_adapter, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Check if the ViewHolder is an instance of your custom ViewHolder

        if (holder is ViewHolder) {
            holder.username.text = unf[position]
            holder.party.text = upf[position]
        }
    }


    override fun getItemCount(): Int {
        return unf.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var username: TextView = view.findViewById(R.id.username)
        var party: TextView = view.findViewById(R.id.party)
    }



}