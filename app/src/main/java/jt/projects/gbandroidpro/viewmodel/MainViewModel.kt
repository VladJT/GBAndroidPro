package jt.projects.gbandroidpro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.observers.DisposableObserver
import jt.projects.gbandroidpro.interactor.MainInteractorImpl
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.utils.INetworkStatus
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val interactor: MainInteractorImpl,
    private val networkStatus: INetworkStatus
) :
    BaseViewModel<AppState>() {

    private var isOnline: Boolean = false

    init {
        Log.d("TAG", "init viewModel")
        compositeDisposable.add(
            networkStatus.isOnline().subscribe() {
                isOnline = it
            })
    }


    // В этой переменной хранится последнее состояние Activity
    private var appState: AppState? = null

    fun getLiveDataForViewToObserve(): LiveData<AppState> {
        return liveDataForViewToObserve
    }


    override fun getData(word: String): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return super.getData(word)
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(t: AppState) {
                appState = t
                liveDataForViewToObserve.value = t
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
                Log.d("TAG", e.message.toString())
            }

            override fun onComplete() {
            //    Log.d("TAG", "DisposableObserver Complete")
            }

        }
    }
}