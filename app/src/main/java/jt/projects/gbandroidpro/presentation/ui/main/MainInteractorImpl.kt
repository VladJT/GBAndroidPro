package jt.projects.gbandroidpro.presentation.ui.main


import jt.projects.repository.Repository
import jt.projects.repository.RepositoryLocal
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.toList


// Снабжаем интерактор репозиторием для получения локальных или внешних данных
class MainInteractorImpl(
    private val repositoryRemote: Repository<Flow<DataModel>>,
    private val repositoryLocal: RepositoryLocal<Flow<DataModel>>
) : jt.projects.core.Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        var appState: AppState
        if (fromRemoteSource) {
            val result = mutableListOf<DataModel>()

            repositoryRemote.getData(word)
                .catch {
                    appState = AppState.Error(it)
                }
                .onCompletion {
                    appState = AppState.Error(Throwable("Поток закрылся"))
                }
                .collect {
                    result.add(it)
                }
            appState = AppState.Success(result)
            repositoryLocal.saveToDb(appState)
        } else {
            appState = AppState.Success(repositoryLocal.getData(word).toList())
        }
        return appState
    }
}

