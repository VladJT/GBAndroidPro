package jt.projects.gbandroidpro.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import jt.projects.gbandroidpro.interactor.MainInteractorImpl
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.utils.network.INetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val interactor: MainInteractorImpl,
    networkStatus: INetworkStatus
) :
    BaseViewModel<AppState>() {

    private var isOnline: Boolean = true

    var counter: MutableLiveData<Int> = MutableLiveData(0)

    init {
        Log.d("TAG", "init viewModel")
        compositeDisposable.add(
            networkStatus.isOnline().subscribe() { status ->
                isOnline = status
            })
    }

    // В этой переменной хранится последнее состояние Activity
    private var appState: AppState? = null

    override fun getData(word: String) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        // Запускаем корутину для асинхронного доступа к серверу с помощью launch
        viewModelCoroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                interactor.getData(word, isOnline)
            }
            _mutableLiveData.value = AppState.Success(response)
        }
    }
    // withContext(Dispatchers.IO) указывает, что доступ в сеть должен
    // осуществляться через диспетчер IO (который предназначен именно для таких
    // операций), хотя это и не обязательно указывать явно, потому что Retrofit
    // и так делает это благодаря CoroutineCallAdapterFactory(). Это же касается и Room


    // Обрабатываем ошибки
    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }


//    override fun getData(word: String): LiveData<AppState> {
//        compositeDisposable.add(
//            interactor.getData(word, isOnline)
//                .subscribeOn(schedulerProvider.io())
//                .observeOn(schedulerProvider.ui())
//                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }
//                .subscribeWith(getObserver())
//        )
//        return super.getData(word)
//    }

//    private fun getObserver(): DisposableObserver<AppState> {
//        return object : DisposableObserver<AppState>() {
//            override fun onNext(t: AppState) {
//                appState = t
//                liveDataForViewToObserve.value = t
//            }
//
//            override fun onError(e: Throwable) {
//                liveDataForViewToObserve.value = AppState.Error(e)
//                Log.d("TAG", e.message.toString())
//            }
//
//            override fun onComplete() {
//                //    Log.d("TAG", "DisposableObserver Complete")
//            }
//
//        }
//    }
}