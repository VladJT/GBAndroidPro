package jt.projects.gbandroidpro.presentation.ui.main


import jt.projects.core.Interactor
import jt.projects.model.data.APPSTATE_ERROR_EMPTY_DATA
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.repository.Repository
import jt.projects.repository.RepositoryLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList


// Снабжаем интерактор репозиторием для получения локальных или внешних данных
open class MainInteractorImpl(
    private val repositoryRemote: Repository<Flow<DataModel>>,
    private val repositoryLocal: RepositoryLocal<Flow<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            val data = repositoryRemote.getDataByWord(word).toList()
            if (data.isEmpty()) {
                appState = APPSTATE_ERROR_EMPTY_DATA
            } else {
                appState = AppState.Success(data)
                repositoryLocal.saveData(data[0])
            }
        } else {
            appState =
                AppState.Success(repositoryLocal.getDataByWord(word).toList())
        }
        return appState
    }
}

