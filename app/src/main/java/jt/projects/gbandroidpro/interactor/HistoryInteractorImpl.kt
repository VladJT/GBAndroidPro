package jt.projects.gbandroidpro.interactor

import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.repository.Repository
import jt.projects.gbandroidpro.model.repository.RepositoryLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

class HistoryInteractorImpl(
    private val repositoryLocal: RepositoryLocal<Flow<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState =
        AppState.Success(repositoryLocal.getData(word).toList())

    suspend fun deleteAll() = repositoryLocal.deleteAll()
}