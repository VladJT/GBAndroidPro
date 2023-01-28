package jt.projects.gbandroidpro.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast


//  registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

// BroadcastReceiver для отслеживания состояния СЕТИ
class NetworkChangeReceiver : BroadcastReceiver() {
    companion object {
        var isConnected = false
    }

    override fun onReceive(context: Context, intent: Intent) {
        val connInfo = getConnectionInfo(context)
        Toast.makeText(context, connInfo, Toast.LENGTH_LONG).show()
    }

    private fun getConnectionInfo(context: Context): String {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        var result = ""
        isConnected = false
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                result = "Сеть: мобильный интернет"
                isConnected = true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                result = "Сеть: WiFi"
                isConnected = true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                result = "Сеть: ETHERNET"
                isConnected = true
            }
        } else result = "Нет сети"
        return result
    }
}