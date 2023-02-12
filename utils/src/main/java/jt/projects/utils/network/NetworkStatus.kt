package jt.projects.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import jt.projects.utils.network.INetworkStatus
import org.koin.core.component.KoinComponent

/**
 * KoinComponent — контейнер зависимостей, с которым вы можете взаимодействовать в классах, не
имеющих своего жизненного цикла. В Activity, сервисе или фрагменте он не нужен, а вот в других
классах для получения зависимостей его нужно имплементировать. После этого вы получаете в своём
приложении доступ к таким функциям, как get, inject, getKoin, viewModel и т. д.

 */
class NetworkStatus(context: Context) : INetworkStatus, KoinComponent {

    override var isOnline: Boolean = false

    private val statusSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    init {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         //   getKoin().get<App>().applicationContext
         //       .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        statusSubject.onNext(false)
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) = statusSubject.onNext(true)
                override fun onUnavailable() = statusSubject.onNext(false)
                override fun onLost(network: Network) = statusSubject.onNext(false)
            })

        statusSubject.subscribe() { status ->
            isOnline = status
        }
    }

    override fun isOnline(): Observable<Boolean> = statusSubject
    override fun isOnlineSingle(): Single<Boolean> = statusSubject.first(false)
}