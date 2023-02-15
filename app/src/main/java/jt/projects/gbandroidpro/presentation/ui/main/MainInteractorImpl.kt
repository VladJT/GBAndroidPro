package jt.projects.gbandroidpro.presentation.ui.main


import jt.projects.core.Interactor
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.model.data.SearchResultDTO
import jt.projects.repository.Repository
import jt.projects.repository.RepositoryLocal
import jt.projects.repository.mapSearchResultToDataModel
import jt.projects.repository.toDataModel
import kotlinx.coroutines.flow.*


// Снабжаем интерактор репозиторием для получения локальных или внешних данных
class MainInteractorImpl(
    private val repositoryRemote: Repository<Flow<SearchResultDTO>>,
    private val repositoryLocal: RepositoryLocal<Flow<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        var appState: AppState = AppState.Error(Throwable("some error in MainInteractorImpl"))//deafult

        if (fromRemoteSource) {
            val result = mutableListOf<SearchResultDTO>()

            repositoryRemote.getTranslationByWord(word)
                .catch {
                    appState = AppState.Error(it)
                }
                .onCompletion {
                    if(result.size>0) {
                        appState = AppState.Success(mapSearchResultToDataModel(result))
                        appState.toDataModel()?.let { repositoryLocal.saveData(it) }
                    }else{
                        appState = AppState.Error(Throwable("Перевод не найден"))
                    }
                }
                .collect {
                    result.add(it)
                }

        } else {
            appState =
                AppState.Success(repositoryLocal.getTranslationByWord(word).toList())
        }
        return appState
    }
}

