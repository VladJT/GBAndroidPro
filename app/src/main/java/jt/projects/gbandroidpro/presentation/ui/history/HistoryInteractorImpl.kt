package jt.projects.gbandroidpro.presentation.ui.history

import jt.projects.repository.RepositoryLocal
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

class HistoryInteractorImpl(
    private val repositoryLocal: RepositoryLocal<Flow<DataModel>>
) : jt.projects.core.Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState =
        AppState.Success(repositoryLocal.getData(word).toList())

    suspend fun deleteAll() = repositoryLocal.deleteAll()
}