package jt.projects.utils.network

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface INetworkStatus {
    fun isOnlineObservable(): Observable<Boolean>
    fun isOnline(): Boolean
}
