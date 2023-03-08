package jt.projects.gbandroidpro.presentation.ui.history

import jt.projects.core.Interactor
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.repository.RepositoryLocal
import jt.projects.repository.RepositoryLocalImpl
import jt.projects.repository.fake.FakeDataSourceLocalImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

class HistoryInteractorImpl(
    private val repositoryLocal: RepositoryLocal<Flow<DataModel>>
) : Interactor<AppState> {

    private val fakeRepo  = RepositoryLocalImpl(FakeDataSourceLocalImpl())

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState =
        AppState.Success(fakeRepo.getDataByWord(word).toList())

    suspend fun getAllData(): AppState =
        AppState.Success(fakeRepo.getAllData().toList())

    suspend fun deleteAll() = fakeRepo.clearAllData()
}