package jt.projects.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class OnlineLiveData(context: Context) : LiveData<Boolean>() {
    //Массив из доступных сетей
    private val availableNetworks = mutableSetOf<Network>()

    // Получаем connectivityManager
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as
            ConnectivityManager

    // Создаём запрос
    private val request: NetworkRequest = NetworkRequest.Builder().build()

    // Создаём колбэк, который уведомляет нас о появлении или исчезновении связи с сетью
    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            availableNetworks.remove(network)
            update(availableNetworks.isNotEmpty())
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            availableNetworks.add(network)
            update(availableNetworks.isNotEmpty())
        }
    }

    // Регистрируем колбэк, если компонент, подписанный на LiveData, активен
    override fun onActive() {
        connectivityManager.registerNetworkCallback(request, callback)
    }

    // Убираем колбэк, если компонент, подписанный на LiveData, неактивен
    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(callback)
    }

    private fun update(online: Boolean) {
        if (online != value) {
            postValue(online)
        }
    }
}