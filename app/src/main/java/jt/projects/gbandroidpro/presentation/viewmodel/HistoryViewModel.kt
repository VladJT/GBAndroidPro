package jt.projects.gbandroidpro.presentation.viewmodel

import jt.projects.gbandroidpro.interactor.HistoryInteractorImpl
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.utils.network.INetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(
    private val interactor: HistoryInteractorImpl,
    private val networkStatus: INetworkStatus
) :
    BaseViewModel<AppState>() {

    override fun getData(word: String) {
        liveData.value = AppState.Loading(null)
        cancelJob()

        viewModelCoroutineScope.launch {
            withContext(Dispatchers.IO) {
               // val response = interactor.getData(word, networkStatus.isOnline)
                val response = interactor.getData(word, false)
                liveData.postValue(response)
            }
        }
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
