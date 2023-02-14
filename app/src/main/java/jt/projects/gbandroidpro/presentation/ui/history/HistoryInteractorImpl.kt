package jt.projects.gbandroidpro.presentation.ui.history

import jt.projects.core.Interactor
import jt.projects.model.data.AppState
import jt.projects.model.data.SearchResultDTO
import jt.projects.repository.RepositoryLocal
import jt.projects.repository.mapSearchResultToDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

class HistoryInteractorImpl(
    private val repositoryLocal: RepositoryLocal<Flow<SearchResultDTO>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState =
        AppState.Success(mapSearchResultToDataModel(repositoryLocal.getData(word).toList()))

    suspend fun getAllData(): AppState =
        AppState.Success(mapSearchResultToDataModel(repositoryLocal.getAllData().toList()))

    suspend fun deleteAll() = repositoryLocal.deleteAll()
}