package jt.projects.gbandroidpro.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import jt.projects.gbandroidpro.interactor.MainInteractorImpl
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.utils.network.INetworkStatus
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val interactor: MainInteractorImpl,
    networkStatus: INetworkStatus
) :
    BaseViewModel<AppState>() {

    private var isOnline: Boolean = true
    val queryStateFlow = MutableStateFlow("")

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
        viewModelCoroutineScope.launch {
            val result = mutableListOf<DataModel>()
            queryStateFlow.debounce(1000)
                .filter { word ->     //фильтрует пустые строки.
                    return@filter word.isNotEmpty()
                }
                .distinctUntilChanged()       //позволяет избегать дублирующие запросы
                .flatMapLatest { word ->// возвращает в поток только самый последний запрос и игнорирует более ранние
                    _mutableLiveData.value = AppState.Loading(null)
                    result.clear()

                    interactor.getData(word, isOnline).catch {
                        _mutableLiveData.postValue(AppState.Error(it))
                    }
                }.onCompletion {
                    _mutableLiveData.postValue(AppState.Error(Throwable("Поток закрылся")))
                }
                .collect {
                    result.add(it)
                    _mutableLiveData.postValue(AppState.Success(result))
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