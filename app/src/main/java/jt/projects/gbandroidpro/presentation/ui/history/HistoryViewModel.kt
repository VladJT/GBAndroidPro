package jt.projects.gbandroidpro.presentation.ui.history

import jt.projects.core.BaseViewModel
import jt.projects.model.data.AppState
import jt.projects.utils.network.INetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val interactor: HistoryInteractorImpl,
    private val networkStatus: INetworkStatus
) :
    BaseViewModel<AppState>() {

    override fun getData(word: String) {
        liveData.value = AppState.Loading(null)
        cancelJob()

        viewModelCoroutineScope.launch {
            // val response = interactor.getData(word, networkStatus.isOnline)
            val response = interactor.getAllData()
            handleResponse(response)
        }
    }

    fun cleanHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            interactor.deleteAll()
            liveData.postValue(AppState.Success(null))
        }
    }

    // Обрабатываем ошибки
    override fun handleError(error: Throwable) {
        liveData.postValue(AppState.Error(error))
        cancelJob()
    }

    override fun handleResponse(response: AppState) {
        liveData.postValue(response)
    }

    override suspend fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun onCleared() {
        liveData.value = AppState.Success(null)
        super.onCleared()
    }
}
