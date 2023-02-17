package jt.projects.utils.network

import io.reactivex.rxjava3.core.Observable

interface INetworkStatus {
    fun isOnlineObservable(): Observable<Boolean>
    fun isOnline(): Boolean
}
