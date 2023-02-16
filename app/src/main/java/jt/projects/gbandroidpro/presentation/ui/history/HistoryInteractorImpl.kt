package jt.projects.gbandroidpro.presentation.ui.history

import jt.projects.core.Interactor
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.repository.RepositoryLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

class HistoryInteractorImpl(
    private val repositoryLocal: RepositoryLocal<Flow<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState =
        AppState.Success(repositoryLocal.getDataByWord(word).toList())

    suspend fun getAllData(): AppState =
        AppState.Success(repositoryLocal.getAllData().toList())

    suspend fun deleteAll() = repositoryLocal.clearAllData()
}