package jt.projects.gbandroidpro.presentation.ui.main

import android.util.Log
import jt.projects.core.BaseViewModel
import jt.projects.gbandroidpro.di.saveWordToSharedPref
import jt.projects.model.data.AppState
import jt.projects.utils.network.INetworkStatus
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainViewModel(
    private val interactor: MainInteractorImpl,
    private val networkStatus: INetworkStatus
) :
    BaseViewModel<AppState>() {

    private val queryStateFlow = MutableStateFlow("")

    init {
        Log.d("TAG", "init viewModel")
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
                    liveData.postValue(AppState.Error(Throwable("initQueryStateFlow закрылся")))
                }
                .collect { word ->
                    saveWordToSharedPref(word)
                    loadData(word)
                }
        }
    }

    private fun loadData(word: String) {
        liveData.value = AppState.Loading(0)
        cancelJob()
        viewModelCoroutineScope.launch {
            withContext(Dispatchers.IO) {
                val response = interactor.getData(word, networkStatus.isOnline())
                (1..10).forEach {
                    liveData.postValue(AppState.Loading(it * 10))
                    delay(20)
                }
                liveData.postValue(response)
            }
        }
    }

    override fun getData(word: String) {
        queryStateFlow.value = word
    }

    // Обрабатываем ошибки
    override fun handleError(error: Throwable) {
        liveData.postValue(AppState.Error(error))
        cancelJob()
    }

    override fun onCleared() {
        liveData.value = AppState.Success(null)
        super.onCleared()
    }
}