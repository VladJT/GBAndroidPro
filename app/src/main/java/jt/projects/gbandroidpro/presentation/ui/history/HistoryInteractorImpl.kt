package jt.projects.gbandroidpro.presentation.ui.history

import jt.projects.core.Interactor
import jt.projects.core.LocalInteractor
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.repository.RepositoryLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

open class HistoryInteractorImpl(
    private val repositoryLocal: RepositoryLocal<Flow<DataModel>>
) : LocalInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState =
        AppState.Success(repositoryLocal.getDataByWord(word).toList())

    override suspend fun getAllData(): AppState =
        AppState.Success(repositoryLocal.getAllData().toList())

    override suspend fun deleteAll() = repositoryLocal.clearAllData()
}