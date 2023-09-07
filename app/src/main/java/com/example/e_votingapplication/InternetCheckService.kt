package com.example.e_votingapplication

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.IBinder

class InternetCheckService : Service() {

    private val connectivityReceiver = ConnectivityReceiver()

    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityReceiver, intentFilter)
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectivityReceiver)
    }
}

class ConnectivityReceiver : BroadcastReceiver() {
    var x : Int = 0
    companion object {
        const val FINISH_ACTION = "com.yourpackage.FINISH_ACTION"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (isConnected(context)) {
            // Internet connectivity is available
            if(x == 1){
                val newActivityIntent = Intent(context ,login_page::class.java)
                newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(newActivityIntent)
                x = 0
            }
        } else {
            x  = 1
            val newActivityIntent = Intent(context ,offline_splash::class.java)
            newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(newActivityIntent)
        }
    }

    private fun isConnected(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            val activeNetwork = it.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

        return false
    }

}
