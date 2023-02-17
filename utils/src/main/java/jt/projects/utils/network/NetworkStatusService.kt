package jt.projects.utils.network

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.IBinder
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import jt.projects.utils.NETWORK_STATUS_INTENT_FILTER

class NetworkStatusService() : Service(), INetworkStatus {
    private val statusSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(this, "onBind", Toast.LENGTH_SHORT).show()
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        init()
        // Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show()
        return START_STICKY
    }

    override fun onDestroy() {
        //  Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    private fun init() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // statusSubject.onNext(false)
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) = statusSubject.onNext(true)
                override fun onUnavailable() = statusSubject.onNext(false)
                override fun onLost(network: Network) = statusSubject.onNext(false)
            })

        statusSubject.subscribe() {
            val intent = Intent(NETWORK_STATUS_INTENT_FILTER)
            intent.putExtra("STATUS", it)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }

    override fun isOnlineObservable(): Observable<Boolean> = statusSubject
    override fun isOnline(): Boolean = statusSubject?.value ?: false
}