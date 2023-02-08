package jt.projects.gbandroidpro.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import jt.projects.gbandroidpro.interactor.MainInteractorImpl
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.utils.network.INetworkStatus
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainViewModel(
    private val interactor: MainInteractorImpl,
    networkStatus: INetworkStatus
) :
    BaseViewModel<AppState>() {

    private var isOnline: Boolean = true
    private val queryStateFlow = MutableStateFlow("")

    var counter: MutableLiveData<Int> = MutableLiveData(0)//TEST SAVE STATE

    init {
        Log.d("TAG", "init viewModel")
        compositeDisposable.add(
            networkStatus.isOnline().subscribe() { status ->
                isOnline = status
            })

        initQueryStateFlow()
    }

    // withContext(Dispatchers.IO) указывает, что доступ в сеть должен
    // осуществляться через диспетчер IO (который предназначен именно для таких
    // операций), хотя это и не обязательно указывать явно, потому что Retrofit
    // и так делает это благодаря CoroutineCallAdapterFactory(). Это же касается и Room
    private fun initQueryStateFlow() {
        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            queryStateFlow.debounce(1000)
                .filter { word ->     //фильтрует пустые строки.
                    return@filter word.isNotEmpty()
                }
                .distinctUntilChanged()       //позволяет избегать дублирующие запросы
                .onCompletion {
                    _mutableLiveData.postValue(AppState.Error(Throwable("initQueryStateFlow закрылся")))
                }
                .collect { word ->
                    loadData(word)
                }
        }
    }

    private fun loadData(word: String) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            withContext(Dispatchers.IO) {
                val response = interactor.getData(word, isOnline)
                _mutableLiveData.postValue(response)
            }
        }
    }

    override fun getData(word: String) {
        queryStateFlow.value = word
    }

    // Обрабатываем ошибки
    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
        cancelJob()
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}