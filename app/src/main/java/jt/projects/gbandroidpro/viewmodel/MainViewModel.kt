package jt.projects.gbandroidpro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.observers.DisposableObserver
import jt.projects.gbandroidpro.interactor.MainInteractorImpl
import jt.projects.gbandroidpro.model.datasource.DataSourceLocal
import jt.projects.gbandroidpro.model.datasource.DataSourceRemote
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.model.repository.RepositoryImpl

class MainViewModel() : BaseViewModel<AppState>() {
    private val interactor: MainInteractorImpl = MainInteractorImpl(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    )

    // В этой переменной хранится последнее состояние Activity
    private var appState: AppState? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
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
                Log.d("TAG", "DisposableObserver Complete")
            }

        }
    }
}